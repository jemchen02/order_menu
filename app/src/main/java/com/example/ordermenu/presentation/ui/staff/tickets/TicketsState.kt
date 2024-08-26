package com.example.ordermenu.presentation.ui.staff.tickets

import com.example.ordermenu.domain.model.dish.Dish
import com.example.ordermenu.domain.model.order.OrderTicket

data class TicketsState(
    val currTicket: OrderTicket? = null,
    val showDialog: Boolean = false
)