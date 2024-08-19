package com.example.ordermenu.data.local.repository

import com.example.ordermenu.data.local.dao.CategoryDao
import com.example.ordermenu.data.local.table.Category
import com.example.ordermenu.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalCategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
): CategoryRepository {
    override suspend fun addCategory(category: Category) =
        categoryDao.addCategory(category)

    override fun getAllCategories(): Flow<List<Category>> =
        categoryDao.getAllCategories()

}