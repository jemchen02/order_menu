package com.example.ordermenu.domain.repository

import com.example.ordermenu.domain.model.restaurant.Restaurant
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    val restaurantId: Flow<String?>

    suspend fun saveId(id: String)

    suspend fun clearId()
}