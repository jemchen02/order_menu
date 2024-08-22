package com.example.ordermenu.presentation.ui.menu.customer

import com.example.ordermenu.domain.model.category.DishCategory
import com.example.ordermenu.domain.model.dish.Dish
import com.example.ordermenu.domain.model.order.Order

data class CustomerMenuState(
    val dishes: List<Dish> = emptyList(),
    val order: Order = Order(),
    val showOrder: Boolean = false,
    val categories: List<DishCategory> = emptyList(),
    val category: DishCategory? = null
)
