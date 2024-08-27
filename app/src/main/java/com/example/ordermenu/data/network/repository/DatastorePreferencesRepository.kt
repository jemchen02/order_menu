package com.example.ordermenu.data.network.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.ordermenu.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatastorePreferencesRepository @Inject constructor(
    private val datastore: DataStore<Preferences>
): PreferencesRepository {
    companion object {
        val RESTAURANT = stringPreferencesKey("restaurant")
    }

    override val restaurantId: Flow<String?> = datastore.data
        .map { preferences ->
            preferences[RESTAURANT]
        }

    override suspend fun saveId(id: String) {
        datastore.edit { preferences ->
            preferences[RESTAURANT] = id
        }
    }

    override suspend fun clearId() {
        datastore.edit { preferences ->
            preferences.remove(RESTAURANT)
        }
    }
}