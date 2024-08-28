package com.example.ordermenu.presentation.ui.staff.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.NoCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ordermenu.R
import com.example.ordermenu.presentation.ui.components.OrderMenuAppBar
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.lightspark.composeqr.QrCodeView
import kotlinx.coroutines.launch

@Composable
fun AuthPage(
    enterNextPage: () -> Unit
) {
    val viewModel = hiltViewModel<AuthViewModel>()
    val restaurantId = viewModel.getRestaurantId().collectAsState(null).value
    restaurantId?.let {
        enterNextPage()
    }
    val context = LocalContext.current
    Scaffold(
        topBar = {
            OrderMenuAppBar()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                viewModel.signIn(
                    onError = {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    }
                )
            }) {
                Text("Login")
            }
        }
    }
}