package com.example.ordermenu.presentation.ui.staff.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordermenu.data.network.repository.FirebaseRestaurantRepository
import com.example.ordermenu.domain.repository.PreferencesRepository
import com.example.ordermenu.domain.service.LoginService
import com.example.ordermenu.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginService: LoginService,
    private val preferencesRepository: PreferencesRepository,
    private val restaurantRepository: FirebaseRestaurantRepository
): ViewModel() {
    fun getRestaurantId(): Flow<String?> = preferencesRepository.restaurantId

    fun signIn(
        onError: (String) -> Unit
    ) = viewModelScope.launch {
        when(val loginResult = loginService.signIn()) {
            is Resource.Success -> {
                val userId = loginResult.data?.uid
                userId?.let {
                    val restaurant = restaurantRepository.getRestaurantByUserId(userId)
                    if(restaurant != null) {
                        preferencesRepository.saveId(restaurant.id)
                    } else {
                        onError("Error finding restaurant")
                    }
                }
            }
            is Resource.Error -> onError(loginResult.message ?: "Error signing in")
        }
    }
}