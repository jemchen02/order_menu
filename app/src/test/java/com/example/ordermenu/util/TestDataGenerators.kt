package com.example.ordermenu.util

import com.example.ordermenu.domain.model.category.DishCategory
import com.example.ordermenu.domain.model.dish.Dish
import com.example.ordermenu.domain.model.dish.DishEntry

fun dishEntry(id: String): DishEntry = DishEntry(
    id = id,
    name = "Bread",
    categoryId = "categoryId",
    allergens = "wheat",
    price = "3.5",
    calories = "100",
    imageURL = "imageURL"
)
fun dish(id: String): Dish = Dish(
    id = id,
    name = "Bread",
    categoryId = "categoryId",
    allergens = "wheat",
    price = 3.5,
    calories = 100,
    imageURL = "imageURL"
)
fun category(restaurantId: String): DishCategory = DishCategory(
    restaurantId = restaurantId,
    name = "Breakfast"
)