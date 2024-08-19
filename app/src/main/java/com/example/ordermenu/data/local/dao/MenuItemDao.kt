package com.example.ordermenu.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.ordermenu.data.local.table.MenuItem
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuItemDao {
    @Insert
    suspend fun addMenuItem(menuItem: MenuItem)

    @Delete
    suspend fun deleteMenuItem(menuItem: MenuItem)

    @Query("SELECT * FROM menu_items WHERE categoryId = :categoryId")
    fun getMenuItemsByCategoryId(categoryId: String): Flow<List<MenuItem>>

    @Query("SELECT * FROM menu_items")
    fun getAllMenuItems(): Flow<List<MenuItem>>
}