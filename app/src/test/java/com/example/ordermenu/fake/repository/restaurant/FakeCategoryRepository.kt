package com.example.ordermenu.fake.repository.restaurant

import com.example.ordermenu.domain.model.category.DishCategory
import com.example.ordermenu.domain.repository.restaurant.CategoryRepository

class FakeCategoryRepository: CategoryRepository {
    private val categories = mutableListOf<DishCategory>()
    override suspend fun getAllCategories(): List<DishCategory> {
        return categories
    }

    override suspend fun getAllCategoriesByRestaurantId(id: String): List<DishCategory> {
        return categories.filter {
            it.restaurantId == id
        }
    }

    override suspend fun addCategory(category: DishCategory) {
        categories.add(category)
    }

    override suspend fun deleteCategory(id: String) {
        categories.removeAll { it.id == id }
    }
}