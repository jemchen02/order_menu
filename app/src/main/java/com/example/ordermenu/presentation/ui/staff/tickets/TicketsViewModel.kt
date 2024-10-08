package com.example.ordermenu.presentation.ui.staff.tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordermenu.data.network.repository.preferences.DatastorePreferencesRepository
import com.example.ordermenu.domain.model.order.OrderTicket
import com.example.ordermenu.domain.repository.restaurant.OrderRepository
import com.example.ordermenu.domain.repository.preferences.PreferencesRepository
import com.example.ordermenu.domain.repository.restaurant.RestaurantRepository
import com.example.ordermenu.domain.service.ToastService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketsViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val preferencesRepository: PreferencesRepository,
    private val restaurantRepository: RestaurantRepository,
    private val toastService: ToastService
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
                    }
                }
            }
        }
    }

    fun getAllTickets(): Flow<List<OrderTicket>> = flow {
        val restaurantId = _ticketsState.value.restaurant?.id
        if(restaurantId != null) {
            emitAll(orderRepository.getAllOrdersByRestaurantId(restaurantId))
        } else {
            emit(emptyList())
        }
    }
    fun toggleDeleteDialog() {
        _ticketsState.update {
            it.copy(
                showDeleteDialog = !it.showDeleteDialog
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
        toastService.showToast("Ticket deleted")
    }
}