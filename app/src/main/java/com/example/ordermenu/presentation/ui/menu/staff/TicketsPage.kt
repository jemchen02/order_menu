package com.example.ordermenu.presentation.ui.menu.staff

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ordermenu.presentation.ui.components.OrderMenuAppBar
import com.example.ordermenu.presentation.ui.menu.staff.components.TicketList

@Composable
fun TicketsPage() {
    val viewModel = hiltViewModel<TicketsViewModel>()
    val tickets = viewModel.getAllTickets().collectAsState(emptyList()).value
    Scaffold(
        topBar = {
            OrderMenuAppBar()
        }
    ) { innerPadding ->
        TicketList(
            contentPaddingValues = innerPadding,
            tickets = tickets
        )
    }
}