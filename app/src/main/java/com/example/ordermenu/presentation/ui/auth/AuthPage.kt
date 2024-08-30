package com.example.ordermenu.presentation.ui.auth

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.ordermenu.presentation.ClientActivity
import com.example.ordermenu.presentation.StaffActivity
import com.example.ordermenu.presentation.ui.common.OrderMenuAppBar

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