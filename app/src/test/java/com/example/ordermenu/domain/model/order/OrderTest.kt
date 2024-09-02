package com.example.ordermenu.domain.model.order

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.doesNotContain
import assertk.assertions.isEqualTo
import com.example.ordermenu.domain.model.dish.Dish
import com.example.ordermenu.util.dish
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

class OrderTest {
    private lateinit var order: Order
    private lateinit var dish: Dish
    @BeforeEach
    fun setUp() {
        order = Order()
        dish = dish(UUID.randomUUID().toString())
    }
    @Test
    fun `order add dishes succeeds`() {
        order.addDish(dish)
        order.addDish(dish)
        assertThat(order.items[dish]).isEqualTo(2)
        assertThat(order.totalitems).isEqualTo(2)
        assertThat(order.totalPrice).isEqualTo(dish.price * 2)
    }
    @Test
    fun `order remove only dish succeeds`() {
        order.addDish(dish)
        assertThat(order.items.keys).contains(dish)
        order.removeDish(dish)
        assertThat(order.items.keys).doesNotContain(dish)
        assertThat(order.totalitems).isEqualTo(0)
        assertThat(order.totalPrice).isEqualTo(0.0)
    }
    @Test
    fun `order remove a dish from two succeeds`() {
        order.addDish(dish)
        order.addDish(dish)
        order.removeDish(dish)
        assertThat(order.items[dish]).isEqualTo(1)
        assertThat(order.totalitems).isEqualTo(1)
        assertThat(order.totalPrice).isEqualTo(dish.price)
    }
    @Test
    fun `order remove nonexistent dish does nothing`() {
        order.removeDish(dish)
        assertThat(order).isEqualTo(Order(order.id))
    }
}