package com.example.ordermenu.domain.model

data class MenuItem(
    val name: String,
    val ingredients: List<String>,
    val price: Double,
    val calories: Int,
    val imageURL: String
)
