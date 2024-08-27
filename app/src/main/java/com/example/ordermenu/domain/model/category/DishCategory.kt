package com.example.ordermenu.domain.model.category

import java.util.UUID

data class DishCategory(
    val id: String = UUID.randomUUID().toString(),
    val restaurantId: String = "",
    val name: String = ""
)
