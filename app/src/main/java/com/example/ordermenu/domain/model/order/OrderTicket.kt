package com.example.ordermenu.domain.model.order

import com.google.firebase.Timestamp
import java.util.UUID

data class OrderTicket(
    val id: String = UUID.randomUUID().toString(),
    val restaurantId: String = "",
    val items: Map<String, Int> = emptyMap(),
    var totalPrice: Double = 0.0,
    var table: String = "",
    var additionalInstructions: String = "",
    var time: Timestamp = Timestamp.now()
)
