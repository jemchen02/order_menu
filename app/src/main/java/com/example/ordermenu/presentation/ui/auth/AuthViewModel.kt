package com.example.ordermenu.presentation.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordermenu.data.network.repository.preferences.DatastorePreferencesRepository
import com.example.ordermenu.domain.repository.preferences.PreferencesRepository
import com.example.ordermenu.domain.service.LoginService
import com.example.ordermenu.domain.service.ToastService
import com.example.ordermenu.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginService: LoginService,
    private val preferencesRepository: PreferencesRepository,
    private val toastService: ToastService
): ViewModel() {
    fun getUserId(): Flow<String?> = preferencesRepository.getId(
        DatastorePreferencesRepository.USER)

    fun getRestaurantId(): Flow<String?> = preferencesRepository.getId(
        DatastorePreferencesRepository.RESTAURANT
    )

    fun signIn() = viewModelScope.launch {
        when(val loginResult = loginService.signIn()) {
            is Resource.Success -> {
                val userId = loginResult.data?.uid
                userId?.let {
                    preferencesRepository.saveId(userId, DatastorePreferencesRepository.USER)
                }
            }
            is Resource.Error -> toastService.showToast(loginResult.message ?: "Error signing in")
        }
    }

    fun setRestaurantId(restaurantId: String) = viewModelScope.launch {
        preferencesRepository.saveId(restaurantId, DatastorePreferencesRepository.RESTAURANT)
    }
}