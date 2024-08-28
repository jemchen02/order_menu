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
        val USER = stringPreferencesKey("user")
        val RESTAURANT = stringPreferencesKey("restaurant")
    }

    override fun getId(type: Preferences.Key<String>): Flow<String?> {
        return datastore.data.map {
                preferences ->
            preferences[type]
        }
    }

    override suspend fun saveId(id: String, type: Preferences.Key<String>) {
        datastore.edit { preferences ->
            preferences[type] = id
        }
    }

    override suspend fun clearId(type: Preferences.Key<String>) {
        datastore.edit { preferences ->
            preferences.remove(type)
        }
    }
}