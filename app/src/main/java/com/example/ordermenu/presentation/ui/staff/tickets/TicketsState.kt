package com.example.ordermenu.presentation.ui.staff.tickets

import com.example.ordermenu.domain.model.dish.Dish
import com.example.ordermenu.domain.model.order.OrderTicket
import com.example.ordermenu.domain.model.restaurant.Restaurant

data class TicketsState(
    val currTicket: OrderTicket? = null,

    val showDeleteDialog: Boolean = false,

    val restaurant: Restaurant? = null
)