package com.example.ordermenu.di

import com.example.ordermenu.data.network.repository.restaurant.FirebaseCategoryRepository
import com.example.ordermenu.domain.repository.restaurant.ImageRepository
import com.example.ordermenu.data.network.repository.restaurant.FirebaseImageRepository
import com.example.ordermenu.data.network.repository.restaurant.FirebaseOrderRepository
import com.example.ordermenu.data.network.repository.restaurant.FirebaseRestaurantRepository
import com.example.ordermenu.data.network.repository.restaurant.FirestoreDishRepository
import com.example.ordermenu.domain.repository.restaurant.CategoryRepository
import com.example.ordermenu.domain.repository.restaurant.DishRepository
import com.example.ordermenu.domain.repository.restaurant.OrderRepository
import com.example.ordermenu.domain.repository.restaurant.RestaurantRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideImageRepository(): ImageRepository = FirebaseImageRepository()

    @Singleton
    @Provides
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideDishRepository(
        firestore: FirebaseFirestore
    ): DishRepository = FirestoreDishRepository(firestore)

    @Singleton
    @Provides
    fun provideCategoryRepository(
        firestore: FirebaseFirestore
    ): CategoryRepository = FirebaseCategoryRepository(firestore)

    @Singleton
    @Provides
    fun provideOrderRepository(
        firestore: FirebaseFirestore
    ): OrderRepository = FirebaseOrderRepository(firestore)

    @Singleton
    @Provides
    fun provideRestaurantRepository(
        firestore: FirebaseFirestore
    ): RestaurantRepository = FirebaseRestaurantRepository(firestore)
}