package com.example.ordermenu.presentation.ui.menu.staff

import com.example.ordermenu.domain.model.dish.Dish
import com.example.ordermenu.domain.model.order.OrderTicket

data class TicketsState(
    val dishes: List<Dish> = emptyList(),
)