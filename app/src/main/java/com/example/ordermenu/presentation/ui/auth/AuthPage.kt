package com.example.ordermenu.presentation.ui.auth

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ordermenu.presentation.ClientActivity
import com.example.ordermenu.presentation.StaffActivity
import com.example.ordermenu.presentation.ui.auth.components.LoginDialog
import com.example.ordermenu.presentation.ui.auth.components.SignUpDialog
import com.example.ordermenu.presentation.ui.common.ItemTile
import com.example.ordermenu.presentation.ui.common.OrderMenuAppBar

@Composable
fun AuthPage(viewModel: AuthViewModel, scanCode: () -> Unit) {
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
            OrderMenuAppBar(showNavigation = false)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            StaffSection(
                viewModel = viewModel,
                modifier = Modifier.weight(1f)
            )
            VerticalDivider(thickness = 8.dp)
            CustomerSection(
                scanCode = scanCode,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
@Composable
fun StaffSection(
    viewModel: AuthViewModel,
    modifier: Modifier
) {
    val authState = viewModel.authState.collectAsState().value
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "Restaurant Staff",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        Column {
            ItemTile(
                icon = Icons.Default.Edit,
                title = "Create your own restaurant menu"
            )
            ItemTile(
                icon = Icons.Default.Notifications,
                title = "Take orders from customers"
            )
            ItemTile(
                icon = Icons.Default.Share,
                title = "Share your restaurant's QR Code"
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Button(onClick = {
            viewModel.toggleDialog(DialogType.LOGIN)
        }) {
            Text("Log In")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            viewModel.toggleDialog(DialogType.SIGNUP)
        }) {
            Text("Sign Up")
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
    if(authState.showSignUpDialog) {
        SignUpDialog(viewModel = viewModel)
    }
    if(authState.showLoginDialog) {
        LoginDialog(viewModel = viewModel)
    }
}
@Composable
fun CustomerSection(scanCode: () -> Unit, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "Customer",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        Column {
            ItemTile(
                icon = Icons.Default.QrCodeScanner,
                title = "Easily scan to view your restaurant's menu"
            )
            ItemTile(
                icon = Icons.Default.ShoppingCart,
                title = "Create orders for your restaurant"
            )
            ItemTile(
                icon = Icons.Filled.Preview,
                title = "View an order preview with total price"
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Button(onClick = scanCode) {
            Text("Scan Restaurant Code")
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}