package com.example.ordermenu.domain.repository.restaurant

import com.example.ordermenu.domain.model.order.Order
import com.example.ordermenu.domain.model.order.OrderTicket
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getAllOrdersByRestaurantId(restaurantId: String): Flow<List<OrderTicket>>

    suspend fun addOrder(order: Order)

    suspend fun removeOrder(id: String)
}