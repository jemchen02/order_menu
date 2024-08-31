package com.example.ordermenu.di

import android.content.Context
import com.example.ordermenu.data.network.service.ToastServiceImpl
import com.example.ordermenu.domain.service.ToastService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ToastModule {

    @Provides
    @Singleton
    fun provideToastService(
        @ApplicationContext context: Context
    ): ToastService = ToastServiceImpl(context)
}