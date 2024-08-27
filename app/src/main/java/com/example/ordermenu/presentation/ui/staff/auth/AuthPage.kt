package com.example.ordermenu.presentation.ui.staff.auth

import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.NoCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ordermenu.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

@Composable
fun AuthPage(
    enterNextPage: () -> Unit
) {
    val viewModel = hiltViewModel<AuthViewModel>()
    val context = LocalContext.current
    Button(onClick = {
        viewModel.signIn(
            onSuccess = enterNextPage,
            onError = {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        )
    }) {
        Text("Login")
    }
}