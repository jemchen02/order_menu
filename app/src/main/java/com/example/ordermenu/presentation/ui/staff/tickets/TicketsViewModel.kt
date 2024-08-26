package com.example.ordermenu.presentation.ui.staff.tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordermenu.domain.model.order.OrderTicket
import com.example.ordermenu.domain.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketsViewModel @Inject constructor(
    private val orderRepository: OrderRepository
): ViewModel() {
    private val _ticketsState = MutableStateFlow(TicketsState())
    val ticketsState = _ticketsState.asStateFlow()

    fun getAllTickets(): Flow<List<OrderTicket>> =
        orderRepository.getAllOrders()

    fun toggleDialog() {
        _ticketsState.update {
            it.copy(
                showDialog = !it.showDialog
            )
        }
    }

    fun setTicket(ticket: OrderTicket) {
        _ticketsState.update {
            it.copy(
                currTicket = ticket
            )
        }
        toggleDialog()
    }

    fun deleteTicket(orderTicket: OrderTicket) = viewModelScope.launch{
        orderRepository.removeOrder(orderTicket.id)
        toggleDialog()
    }
}