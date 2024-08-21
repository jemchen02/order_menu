package com.example.ordermenu.domain.model.order

data class Order(
    val items: Map<String, Int> = emptyMap(),
    val totalPrice: Double = 0.0,
    val additionalInstructions: String = ""
)
