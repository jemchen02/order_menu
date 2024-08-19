package com.example.ordermenu.domain.repository

import com.example.ordermenu.data.local.table.MenuItem
import kotlinx.coroutines.flow.Flow

interface MenuItemRepository {
    suspend fun addMenuItem(menuItem: MenuItem)

    suspend fun deleteMenuItem(menuItem: MenuItem)

    fun getMenuItemsByCategoryId(id: String): Flow<List<MenuItem>>

    fun getAllMenuItems(): Flow<List<MenuItem>>
}