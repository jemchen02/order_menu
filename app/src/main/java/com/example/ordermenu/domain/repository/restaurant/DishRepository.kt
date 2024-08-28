package com.example.ordermenu.domain.repository.restaurant

import com.example.ordermenu.domain.model.dish.Dish

interface DishRepository {
    suspend fun getAllDishes(): List<Dish>

    suspend fun getDishesByCategoryId(categoryId: String): List<Dish>

    suspend fun getDishById(id: String): Dish?

    suspend fun addDish(dish: Dish)

    suspend fun updateDish(dish: Dish)

    suspend fun deleteDish(id: String)
}