package com.example.ordermenu.presentation.ui.staff.tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordermenu.data.network.repository.DatastorePreferencesRepository
import com.example.ordermenu.domain.model.order.OrderTicket
import com.example.ordermenu.domain.repository.OrderRepository
import com.example.ordermenu.domain.repository.PreferencesRepository
import com.example.ordermenu.domain.repository.RestaurantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketsViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val preferencesRepository: PreferencesRepository,
    private val restaurantRepository: RestaurantRepository
): ViewModel() {
    private val _ticketsState = MutableStateFlow(TicketsState())
    val ticketsState = _ticketsState.asStateFlow()

    init {
        viewModelScope.launch {
            preferencesRepository.getId(DatastorePreferencesRepository.USER).collect { id ->
                id?.let {
                    val restaurant = restaurantRepository.getRestaurantByUserId(id)
                    if(restaurant != null) {
                        _ticketsState.update {
                            it.copy(
                                restaurant = restaurant
                            )
                        }
                        getAllTickets()
                    }
                }
            }
        }
    }

    fun getAllTickets(): Flow<List<OrderTicket>> =
        orderRepository.getAllOrders()

    fun toggleDeleteDialog() {
        _ticketsState.update {
            it.copy(
                showDeleteDialog = !it.showDeleteDialog
            )
        }
    }

    fun toggleQrDialog() {
        _ticketsState.update {
            it.copy(
                showQrDialog = !it.showQrDialog
            )
        }
    }

    fun setTicket(ticket: OrderTicket) {
        _ticketsState.update {
            it.copy(
                currTicket = ticket
            )
        }
        toggleDeleteDialog()
    }

    fun deleteTicket(orderTicket: OrderTicket) = viewModelScope.launch{
        orderRepository.removeOrder(orderTicket.id)
        toggleDeleteDialog()
    }
}