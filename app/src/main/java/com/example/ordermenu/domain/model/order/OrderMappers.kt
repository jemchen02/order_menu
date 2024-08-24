package com.example.ordermenu.domain.model.order

import com.google.firebase.Timestamp

fun Order.toOrderTicket(): OrderTicket {
    return OrderTicket(
        id = id,
        items = items.mapKeys { (order, quantity) -> order.name },
        totalPrice = totalPrice,
        additionalInstructions = additionalInstructions,
        time = Timestamp.now()
    )
}