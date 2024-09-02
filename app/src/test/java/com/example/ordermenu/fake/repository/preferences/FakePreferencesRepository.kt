package com.example.ordermenu.fake.repository.preferences

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.ordermenu.domain.repository.preferences.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

class FakePreferencesRepository: PreferencesRepository {
    private val _preferences = mutableMapOf<Preferences.Key<String>, MutableStateFlow<String?>>()
    override fun getId(type: Preferences.Key<String>): Flow<String?> {
        return _preferences[type] ?: flowOf(null)
    }

    override suspend fun saveId(id: String, type: Preferences.Key<String>) {
        if(_preferences.containsKey(type)) {
            _preferences[type]?.value = id
        } else {
            _preferences[type] = MutableStateFlow(id)
        }
    }

    override suspend fun clearId(type: Preferences.Key<String>) {
        _preferences.remove(type)
    }
}