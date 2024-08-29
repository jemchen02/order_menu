package com.example.ordermenu.presentation.ui.staff.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ordermenu.presentation.ui.staff.StaffNavigationViewModel
import com.example.ordermenu.presentation.ui.staff.tickets.TicketsViewModel
import com.lightspark.composeqr.QrCodeView

@Composable
fun DisplayQrDialog(
    viewModel: StaffNavigationViewModel
) {
    val restaurant = viewModel.navState.collectAsState().value.restaurant
    restaurant?.let {
        AlertDialog(
            onDismissRequest = viewModel::toggleQrDialog,
            title = {
                Text(text = "Share Restaurant")
            },
            text = {
                QrCodeView(
                    data = restaurant.id,
                    modifier = Modifier.size(300.dp)
                )
            },
            confirmButton = {
                TextButton(onClick = viewModel::toggleQrDialog) {
                    Text(text = "Done")
                }
            }
        )
    }
}