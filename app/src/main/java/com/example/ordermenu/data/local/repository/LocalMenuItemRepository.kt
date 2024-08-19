package com.example.ordermenu.data.local.repository

import com.example.ordermenu.data.local.dao.MenuItemDao
import com.example.ordermenu.data.local.table.MenuItem
import com.example.ordermenu.domain.repository.MenuItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalMenuItemRepository @Inject constructor(
    private val menuItemDao: MenuItemDao
): MenuItemRepository{
    override suspend fun addMenuItem(menuItem: MenuItem) =
        menuItemDao.addMenuItem(menuItem)

    override suspend fun deleteMenuItem(menuItem: MenuItem) =
        menuItemDao.deleteMenuItem(menuItem)

    override fun getMenuItemsByCategoryId(id: String): Flow<List<MenuItem>> =
        menuItemDao.getMenuItemsByCategoryId(id)

    override fun getAllMenuItems(): Flow<List<MenuItem>> =
        menuItemDao.getAllMenuItems()
}