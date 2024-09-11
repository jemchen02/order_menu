package com.example.ordermenu.presentation.ui.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.ordermenu.presentation.ui.auth.AuthFieldType
import com.example.ordermenu.presentation.ui.auth.AuthViewModel
import com.example.ordermenu.presentation.ui.auth.DialogType

@Composable
fun LoginDialog(viewModel: AuthViewModel) {
    val authState = viewModel.authState.collectAsState().value
    AlertDialog(
        title = {
            Text(text = "Order Menu")
        },
        text = {
            Column (
                modifier = Modifier.padding(12.dp)
            ){
                TextField(
                    value = authState.email,
                    onValueChange = {
                        viewModel.updateField(AuthFieldType.EMAIL, it)
                    },
                    label = {
                        Text("Email")
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                TextField(
                    value = authState.password,
                    onValueChange = {
                        viewModel.updateField(AuthFieldType.PASSWORD, it)
                    },
                    label = {
                        Text("Password")
                    },
                    visualTransformation = PasswordVisualTransformation()
                )
                TextButton(onClick = { viewModel.toggleDialog(DialogType.RESET_PASSWORD) }) {
                    Text(text = "Forgot password?")
                }
            }
        },
        onDismissRequest = { viewModel.toggleDialog(DialogType.LOGIN)},
        confirmButton = {
            Button(onClick = viewModel::signInPassword) {
                Text(text = "Login")
            }
        },
        containerColor = Color.White
    )
}