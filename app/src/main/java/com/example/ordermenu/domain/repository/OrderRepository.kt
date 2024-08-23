package com.example.ordermenu.domain.repository

import com.example.ordermenu.domain.model.order.Order
import com.example.ordermenu.domain.model.order.OrderTicket

interface OrderRepository {
    suspend fun getAllOrders(): List<OrderTicket>

    suspend fun addOrder(order: Order)

    suspend fun removeOrder(id: String)
}