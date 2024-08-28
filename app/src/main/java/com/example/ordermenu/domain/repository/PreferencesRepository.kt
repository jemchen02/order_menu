package com.example.ordermenu.domain.repository

import androidx.datastore.preferences.core.Preferences
import com.example.ordermenu.domain.model.restaurant.Restaurant
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    fun getId(type: Preferences.Key<String>): Flow<String?>

    suspend fun saveId(id: String, type: Preferences.Key<String>)

    suspend fun clearId(type: Preferences.Key<String>)
}