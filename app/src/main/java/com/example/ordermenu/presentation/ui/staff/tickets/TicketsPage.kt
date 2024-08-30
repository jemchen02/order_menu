package com.example.ordermenu.presentation.ui.staff.tickets

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ordermenu.domain.model.order.OrderTicket
import com.example.ordermenu.presentation.ui.common.OrderMenuAppBar
import com.example.ordermenu.presentation.ui.staff.tickets.components.TicketList
import com.example.ordermenu.presentation.ui.staff.tickets.components.DeleteTicketDialog
import kotlinx.coroutines.delay

@Composable
fun TicketsPage(
    onNavigationClick: () -> Unit
) {
    val viewModel = hiltViewModel<TicketsViewModel>()
    val tickets: List<OrderTicket> = viewModel.getAllTickets().collectAsState(emptyList()).value.sortedBy { it.time }
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