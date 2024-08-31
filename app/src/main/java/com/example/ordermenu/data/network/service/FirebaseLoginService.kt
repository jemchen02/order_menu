package com.example.ordermenu.data.network.service

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.example.ordermenu.domain.service.LoginService
import com.example.ordermenu.domain.util.Resource
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseLoginService @Inject constructor(
    private val auth: FirebaseAuth,
    private val context: Context,
    private val request: GetCredentialRequest,
    private val credentialManager: CredentialManager
): LoginService {
    override suspend fun signIn(): Resource<FirebaseUser> {
        return try {
            val result = credentialManager.getCredential(context, request)
            val credential = result.credential
            val googleIdTokenCredential = GoogleIdTokenCredential
                .createFrom(credential.data)
            val googleIdToken = googleIdTokenCredential.idToken
            val authCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
            val authResult = auth.signInWithCredential(authCredential).await()
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