package com.example.ordermenu.domain.repository

import com.example.ordermenu.domain.model.category.DishCategory

interface CategoryRepository {
    suspend fun getAllCategories(): List<DishCategory>

    suspend fun addCategory(category: DishCategory)

    suspend fun deleteCategory(id: String)
}