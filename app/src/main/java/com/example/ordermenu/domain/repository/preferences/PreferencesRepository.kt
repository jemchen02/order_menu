package com.example.ordermenu.domain.repository.preferences

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    fun getId(type: Preferences.Key<String>): Flow<String?>

    suspend fun saveId(id: String, type: Preferences.Key<String>)

    suspend fun clearId(type: Preferences.Key<String>)
}