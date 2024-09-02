package com.example.ordermenu.fake.repository.restaurant

import com.example.ordermenu.domain.model.order.Order
import com.example.ordermenu.domain.model.order.OrderTicket
import com.example.ordermenu.domain.model.order.toOrderTicket
import com.example.ordermenu.domain.repository.restaurant.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeOrderRepository : OrderRepository {
    private val _orders = mutableListOf<OrderTicket>()

    private val _ordersStateFlow = MutableStateFlow<List<OrderTicket>>(emptyList())

    init {
        _ordersStateFlow.value = _orders
    }

    override fun getAllOrdersByRestaurantId(restaurantId: String): Flow<List<OrderTicket>> {
        return _ordersStateFlow.map { orderList ->
            orderList.filter { it.restaurantId == restaurantId }
        }
    }

    override suspend fun addOrder(order: Order) {
        _orders.add(order.toOrderTicket())
        _ordersStateFlow.value = _orders.toList()
    }

    override suspend fun removeOrder(id: String) {
        _orders.removeAll { it.id == id }
        _ordersStateFlow.value = _orders.toList()
    }
}