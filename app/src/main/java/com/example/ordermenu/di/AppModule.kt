package com.example.ordermenu.di

import android.content.Context
import androidx.credentials.GetCredentialRequest
import com.example.ordermenu.R
import com.example.ordermenu.data.network.repository.FirebaseCategoryRepository
import com.example.ordermenu.domain.repository.ImageRepository
import com.example.ordermenu.data.network.repository.FirebaseImageRepository
import com.example.ordermenu.data.network.repository.FirebaseOrderRepository
import com.example.ordermenu.data.network.repository.FirebaseRestaurantRepository
import com.example.ordermenu.data.network.repository.FirestoreDishRepository
import com.example.ordermenu.data.network.service.FirebaseLoginService
import com.example.ordermenu.domain.repository.CategoryRepository
import com.example.ordermenu.domain.repository.DishRepository
import com.example.ordermenu.domain.repository.OrderRepository
import com.example.ordermenu.domain.repository.RestaurantRepository
import com.example.ordermenu.domain.service.LoginService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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