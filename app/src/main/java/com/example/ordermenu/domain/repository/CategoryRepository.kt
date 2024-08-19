package com.example.ordermenu.domain.repository

import com.example.ordermenu.data.local.table.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun addCategory(category: Category)

    fun getAllCategories(): Flow<List<Category>>
}