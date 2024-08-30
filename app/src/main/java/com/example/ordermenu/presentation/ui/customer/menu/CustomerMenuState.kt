package com.example.ordermenu.presentation.ui.customer.menu

import com.example.ordermenu.domain.model.category.DishCategory
import com.example.ordermenu.domain.model.dish.Dish
import com.example.ordermenu.domain.model.order.Order
import com.example.ordermenu.domain.model.restaurant.Restaurant

data class CustomerMenuState(
    val restaurant: Restaurant? = null,

    val dishes: List<Dish> = emptyList(),

    val order: Order = Order(),
    val showOrder: Boolean = false,

    val categories: List<DishCategory> = emptyList(),
    val category: DishCategory? = null
)
