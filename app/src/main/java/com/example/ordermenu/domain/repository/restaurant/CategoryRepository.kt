package com.example.ordermenu.domain.repository.restaurant

import com.example.ordermenu.domain.model.category.DishCategory

interface CategoryRepository {
    suspend fun getAllCategories(): List<DishCategory>

    suspend fun getAllCategoriesByRestaurantId(id: String): List<DishCategory>

    suspend fun addCategory(category: DishCategory)

    suspend fun deleteCategory(id: String)
}