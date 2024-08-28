package com.example.ordermenu.presentation.ui.staff.tickets.components.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.ordermenu.domain.model.order.OrderTicket
import com.example.ordermenu.presentation.ui.staff.tickets.TicketsViewModel

@Composable
fun DeleteTicketDialog(
    viewModel: TicketsViewModel
) {
    val ticket = viewModel.ticketsState.collectAsState().value.currTicket
    ticket?.let {
        AlertDialog(
            onDismissRequest = viewModel::toggleDeleteDialog,
            title = {
                Text(text = "Delete Ticket")
            },
            text = {
                Text(text = "Are you sure you want to delete the ticket?")
            },
            confirmButton = {
                TextButton(onClick = { viewModel.deleteTicket(ticket) }) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = viewModel::toggleDeleteDialog) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}