package com.example.ordermenu.fake.repository.restaurant

import com.example.ordermenu.domain.model.dish.Dish
import com.example.ordermenu.domain.repository.restaurant.DishRepository

class FakeDishRepository: DishRepository {
    private val _dishes = mutableListOf<Dish>()
    override suspend fun getAllDishes(): List<Dish> {
        return _dishes
    }

    override suspend fun getDishesByCategoryId(categoryId: String): List<Dish> {
        return _dishes.filter { it.categoryId == categoryId }
    }

    override suspend fun getDishById(id: String): Dish? {
        return _dishes.find { it.id == id }
    }

    override suspend fun addDish(dish: Dish) {
        val index = _dishes.indexOfFirst { it.id == dish.id }
        if(index != -1) _dishes[index] = dish
        else _dishes.add(dish)
    }

    override suspend fun deleteDish(id: String) {
        _dishes.removeAll { it.id == id }
    }
}