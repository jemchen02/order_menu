package com.example.ordermenu.data.network.service

import android.content.Context
import android.util.Log
import androidx.credentials.CreateCredentialRequest
import androidx.credentials.CreatePasswordRequest
import androidx.credentials.CredentialManager
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
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
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
            Log.e("auth", e.message ?: "Error creating password credential")
            toastService.showToast("Error creating password credential")
        } catch (e: Exception) {
            toastService.showToast("Error signing up")
        }
    }

    override suspend fun signInPassword(email: String, password: String): Resource<FirebaseUser>{
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val user = authResult.user
            if(user != null) {
                val createPasswordRequest = CreatePasswordRequest(email, password)
                credentialManager.createCredential(context, createPasswordRequest)
            }
            Resource.Success(user)
        } catch (e: CreateCredentialException) {
            Resource.Error("Error creating password credential")
        } catch (e: Exception) {
            Resource.Error("Error signing in")
        }
    }

    override suspend fun signIn(): Resource<FirebaseUser> {
        return try {
            val result = credentialManager.getCredential(context, request)
            val credential = result.credential
            val authResult: AuthResult
            when(credential) {
                is GoogleIdTokenCredential -> {
                    val googleIdTokenCredential = GoogleIdTokenCredential
                        .createFrom(credential.data)
                    val googleIdToken = googleIdTokenCredential.idToken
                    val authCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
                    authResult = auth.signInWithCredential(authCredential).await()
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
            Resource.Error("Login cancelled")
        }  catch(e: Exception) {
            Resource.Error("Error logging in")
        }
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
}