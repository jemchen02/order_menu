package com.example.ordermenu.di

import com.example.ordermenu.domain.repository.ImageRepository
import com.example.ordermenu.data.network.FirebaseImageRepository
import com.example.ordermenu.data.network.FirestoreDishRepository
import com.example.ordermenu.domain.repository.DishRepository
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
}