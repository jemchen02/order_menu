package com.example.ordermenu.domain.model

import java.util.UUID

data class Dish(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val categoryId: String = "",
    val allergens: String = "",
    val price: Double = 0.0,
    val calories: Int = 0,
    val imageURL: String = ""
)
data class DishEntry(
    val name: String = "",
    val categoryId: String = "",
    val allergens: String = "",
    val price: String = "",
    val calories: String = "",
    val imageURL: String = ""
)
fun 
enum class DishFields {
    NAME,
    CATEGORY_ID,
    ALLERGENS,
    PRICE,
    CALORIES,
    IMAGE_URL
}