package com.example.ordermenu.di

import android.content.Context
import android.media.Image
import androidx.room.Room
import com.example.ordermenu.data.local.MenuDatabase
import com.example.ordermenu.data.local.dao.CategoryDao
import com.example.ordermenu.data.local.dao.MenuItemDao
import com.example.ordermenu.data.local.repository.LocalCategoryRepository
import com.example.ordermenu.data.local.repository.LocalMenuItemRepository
import com.example.ordermenu.domain.repository.CategoryRepository
import com.example.ordermenu.domain.repository.ImageRepository
import com.example.ordermenu.domain.repository.MenuItemRepository
import com.example.ordermenu.network.FirebaseImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideMenuDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, MenuDatabase::class.java, "menu_database").build()

    @Singleton
    @Provides
    fun provideCategoryDao(
        database: MenuDatabase
    ) = database.categoryDao()

    @Singleton
    @Provides
    fun provideMenuItemDao(
        database: MenuDatabase
    ) = database.menuItemDao()

    @Singleton
    @Provides
    fun provideCategoryRepository(
        categoryDao: CategoryDao
    ): CategoryRepository = LocalCategoryRepository(categoryDao)

    @Singleton
    @Provides
    fun provideMenuItemRepository(
        menuItemDao: MenuItemDao
    ): MenuItemRepository = LocalMenuItemRepository(menuItemDao)

    @Singleton
    @Provides
    fun provideImageRepository(): ImageRepository = FirebaseImageRepository()
}