package com.example.ordermenu.data.network.service

import android.content.Context
import android.util.Log
import androidx.credentials.CreateCredentialRequest
import androidx.credentials.CreatePasswordRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.PasswordCredential
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.example.ordermenu.domain.service.LoginService
import com.example.ordermenu.domain.service.ToastService
import com.example.ordermenu.domain.util.Resource
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

class FirebaseLoginService @Inject constructor(
    private val auth: FirebaseAuth,
    private val context: Context,
    private val request: GetCredentialRequest,
    private val credentialManager: CredentialManager,
    private val toastService: ToastService
): LoginService {
    override suspend fun signUpPassword(email: String, password: String) {
        try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user
            if(user != null) {
                val createPasswordRequest = CreatePasswordRequest(email, password)
                credentialManager.createCredential(context, createPasswordRequest)
            }
        } catch (e: CreateCredentialException) {
            toastService.showToast("Error creating password credential")
        } catch (e: FirebaseAuthWeakPasswordException) {
            toastService.showToast("Password should contain at least 6 characters")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            toastService.showToast("Invalid email/password")
        } catch (e: IllegalArgumentException) {
            toastService.showToast("Email/password cannot be empty")
        } catch (e: Exception) {
            e.printStackTrace()
            toastService.showToast(e.message ?: "Error signing up")
        }
    }

    override suspend fun signInPassword(email: String, password: String): Resource<FirebaseUser>{
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val user = authResult.user
            Resource.Success(user)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Error signing in")
        }
    }

    override suspend fun signIn(): Resource<FirebaseUser> {
        return try {
            val result = credentialManager.getCredential(context, request)
            val credential = result.credential
            val authResult: AuthResult
            when(credential) {
                is CustomCredential -> {
                    if(credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                        val googleIdToken = googleIdTokenCredential.idToken
                        val authCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
                        authResult = auth.signInWithCredential(authCredential).await()
                    } else return Resource.Error("Invalid google id token")
                }
                is PasswordCredential -> {
                    val username = credential.id
                    val password = credential.password
                    authResult = auth.signInWithEmailAndPassword(username, password).await()
                }
                else -> return Resource.Error("Unexpected credential type")
            }
            val user = authResult.user
            if(user != null) {
                Resource.Success(user)
            } else {
                Resource.Error("Authenticated user is null")
            }
        } catch(e: GetCredentialCancellationException) {
            Resource.Error("Credential login canceled")
        } catch(e: Exception) {
            Resource.Error(e.message ?:"Error logging in")
        }
    }

    override fun signOut() {
        auth.signOut()
    }

    override suspend fun resetPassword(email: String) {
        try {
            auth.sendPasswordResetEmail(email)
            toastService.showToast("Password reset email sent to $email")
        } catch (e: Exception) {
            toastService.showToast(e.message ?: "Error resetting password")
        }
    }

    override suspend fun savePassword(email: String, password: String) {
        try {
            val createPasswordRequest = CreatePasswordRequest(email, password)
            credentialManager.createCredential(context, createPasswordRequest)
        } catch (e: CreateCredentialException) {
            toastService.showToast("Did not create credential")
        } catch (e: Exception) {
            e.printStackTrace()
            toastService.showToast("Error creating credential")
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
}