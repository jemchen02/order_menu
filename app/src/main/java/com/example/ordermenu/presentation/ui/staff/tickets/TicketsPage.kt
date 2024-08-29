package com.example.ordermenu.presentation.ui.staff.tickets

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ordermenu.presentation.ui.components.OrderMenuAppBar
import com.example.ordermenu.presentation.ui.staff.tickets.components.TicketList
import com.example.ordermenu.presentation.ui.staff.tickets.components.DeleteTicketDialog

@Composable
fun TicketsPage(
    onNavigationClick: () -> Unit
) {
    val viewModel = hiltViewModel<TicketsViewModel>()
    val tickets = viewModel.getAllTickets().collectAsState(emptyList()).value
    val ticketsState = viewModel.ticketsState.collectAsState().value
    val showDeleteDialog = ticketsState.showDeleteDialog
    
    Scaffold(
        topBar = {
            OrderMenuAppBar(
                onNavigationClick = onNavigationClick
            )
        }
    ) { innerPadding ->
        TicketList(
            contentPaddingValues = innerPadding,
            tickets = tickets,
            onTicketClick = viewModel::setTicket
        )
        if(showDeleteDialog) {
            DeleteTicketDialog(viewModel = viewModel)
        }
    }
}