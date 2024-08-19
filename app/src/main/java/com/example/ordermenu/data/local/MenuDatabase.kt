package com.example.ordermenu.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ordermenu.data.local.dao.CategoryDao
import com.example.ordermenu.data.local.dao.MenuItemDao
import com.example.ordermenu.data.local.table.Category
import com.example.ordermenu.data.local.table.MenuItem

@Database(
    entities = arrayOf(Category::class, MenuItem::class),
    version = 1,
    exportSchema = false
)
abstract class MenuDatabase: RoomDatabase(){
    abstract fun categoryDao(): CategoryDao
    abstract fun menuItemDao(): MenuItemDao
    companion object {
        @Volatile
        private var INSTANCE: MenuDatabase? = null
        fun getDatabase(context: Context): MenuDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    MenuDatabase::class.java,
                    "menu_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}