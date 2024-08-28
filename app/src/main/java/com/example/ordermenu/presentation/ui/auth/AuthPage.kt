package com.example.ordermenu.presentation.ui.auth

import android.content.Intent
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
import com.example.ordermenu.presentation.ClientActivity
import com.example.ordermenu.presentation.StaffActivity
import com.example.ordermenu.presentation.ui.components.OrderMenuAppBar
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.lightspark.composeqr.QrCodeView
import kotlinx.coroutines.launch

@Composable
fun AuthPage(
    viewModel: AuthViewModel,
    scanCode: () -> Unit
) {
    val context = LocalContext.current
    val userId = viewModel.getUserId().collectAsState(null).value
    userId?.let {
        val intent = Intent(context, StaffActivity::class.java)
        context.startActivity(intent)
    }
    val restaurantId = viewModel.getRestaurantId().collectAsState(null).value
    restaurantId?.let {
        val intent = Intent(context, ClientActivity::class.java)
        context.startActivity(intent)
    }
    Scaffold(
        topBar = {
            OrderMenuAppBar()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                Text("Staff: Login")
            }
            Button(onClick = scanCode) {
                Text("Client: Scan Restaurant Code")
            }
        }
    }
}