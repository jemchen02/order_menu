package com.example.ordermenu.presentation.ui.staff.tickets

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ordermenu.presentation.ui.components.OrderMenuAppBar
import com.example.ordermenu.presentation.ui.staff.tickets.components.TicketList
import com.example.ordermenu.presentation.ui.staff.tickets.components.dialog.DeleteTicketDialog

@Composable
fun TicketsPage(
    editMenu: () -> Unit
) {
    val viewModel = hiltViewModel<TicketsViewModel>()
    val tickets = viewModel.getAllTickets().collectAsState(emptyList()).value
    val showDialog = viewModel.ticketsState.collectAsState().value.showDialog
    Scaffold(
        topBar = {
            OrderMenuAppBar() {
                IconButton(onClick = editMenu) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Open menu editor",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }

    ) { innerPadding ->
        TicketList(
            contentPaddingValues = innerPadding,
            tickets = tickets,
            onTicketClick = viewModel::setTicket
        )
        if(showDialog) {
            DeleteTicketDialog(viewModel = viewModel)
        }
    }
}