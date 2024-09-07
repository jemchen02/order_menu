package com.example.ordermenu.presentation.ui.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.ordermenu.presentation.ui.auth.AuthFieldType
import com.example.ordermenu.presentation.ui.auth.AuthViewModel
import com.example.ordermenu.presentation.ui.auth.DialogType

@Composable
fun LoginDialog(viewModel: AuthViewModel) {
    val authState = viewModel.authState.collectAsState().value
    AlertDialog(
        title = {
            Text(text = "Order Menu Login")
        },
        text = {
            Column {
                TextField(
                    value = authState.email,
                    onValueChange = {
                        viewModel.updateField(AuthFieldType.EMAIL, it)
                    },
                    label = {
                        Text("Email")
                    }
                )
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
            }
        },
        onDismissRequest = { viewModel.toggleDialog(DialogType.LOGIN)},
        confirmButton = {
            Button(onClick = viewModel::signInPassword) {
                Text(text = "Login")
            }
        }
    )
}