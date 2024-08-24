package com.example.ordermenu.presentation.ui.menu.staff

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordermenu.data.network.FirebaseOrderRepository
import com.example.ordermenu.domain.model.order.OrderTicket
import com.example.ordermenu.domain.repository.DishRepository
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
    fun getAllTickets(): Flow<List<OrderTicket>> =
        orderRepository.getAllOrders()
}