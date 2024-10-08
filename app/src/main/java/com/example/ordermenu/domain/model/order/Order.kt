package com.example.ordermenu.domain.model.order

import com.example.ordermenu.domain.model.dish.Dish
import java.util.UUID

data class Order(
    val id: String = UUID.randomUUID().toString(),
    val restaurantId: String = "",
    val items: MutableMap<Dish, Int> = mutableMapOf(),
    var totalitems: Int = 0,
    var totalPrice: Double = 0.0,
    var table: String = "",
    var additionalInstructions: String = ""
) {
    fun addDish(dish: Dish) {
        val currQuantity = items[dish] ?: 0
        items[dish] = currQuantity + 1
        totalitems++
        calculatePrice()
    }
    fun removeDish(dish: Dish) {
        val currQuantity = items[dish] ?: 0
        if(currQuantity > 1) {
            items[dish] = currQuantity - 1
            totalitems--
        } else if(currQuantity == 1) {
            items.remove(dish)
            totalitems--
        }
        calculatePrice()
    }
    private fun calculatePrice() {
        totalPrice = items.entries.sumOf { (dish, quantity) ->
            dish.price * quantity
        }
    }
}