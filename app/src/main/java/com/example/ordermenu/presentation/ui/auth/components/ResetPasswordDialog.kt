package com.example.ordermenu.presentation.ui.auth.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import com.example.ordermenu.presentation.ui.auth.AuthFieldType
import com.example.ordermenu.presentation.ui.auth.AuthViewModel
import com.example.ordermenu.presentation.ui.auth.DialogType

@Composable
fun ResetPasswordDialog(viewModel: AuthViewModel) {
    val authState = viewModel.authState.collectAsState().value
    AlertDialog(
        title = {
            Text(text = "Order Menu")
        },
        text = {
            TextField(
                value = authState.email,
                onValueChange = {
                    viewModel.updateField(AuthFieldType.EMAIL, it)
                },
                label = {
                    Text("Email")
                }
            )
        },
        onDismissRequest = { viewModel.toggleDialog(DialogType.RESET_PASSWORD) },
        confirmButton = {
            Button(onClick = viewModel::resetPassword) {
                Text(text = "Reset Password")
            }
        },
        containerColor = Color.White
    )
}