package com.example.ordermenu.presentation.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordermenu.data.network.repository.preferences.DatastorePreferencesRepository
import com.example.ordermenu.domain.repository.preferences.PreferencesRepository
import com.example.ordermenu.domain.service.LoginService
import com.example.ordermenu.domain.service.ToastService
import com.example.ordermenu.domain.util.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginService: LoginService,
    private val preferencesRepository: PreferencesRepository,
    private val toastService: ToastService
): ViewModel() {
    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    fun getUserId(): Flow<String?> = preferencesRepository.getId(
        DatastorePreferencesRepository.USER)

    fun getRestaurantId(): Flow<String?> = preferencesRepository.getId(
        DatastorePreferencesRepository.RESTAURANT
    )
    fun updateField(type: AuthFieldType, value: String) {
        _authState.update {
            when(type) {
                AuthFieldType.EMAIL -> it.copy(email = value)
                AuthFieldType.PASSWORD -> it.copy(password = value)
            }
        }
    }
    fun signUp() = viewModelScope.launch {
        loginService.signUpPassword(
            _authState.value.email,
            _authState.value.password
        )
        toggleDialog(DialogType.SIGNUP)
    }
    fun signInCredential() = viewModelScope.launch {
        val loginResult = loginService.signIn()
        saveUser(loginResult)
    }
    fun signInPassword() = viewModelScope.launch {
        val loginResult = loginService.signInPassword(
            _authState.value.email,
            _authState.value.password
        )
        if(loginResult is Resource.Success) {
            loginService.savePassword(
                _authState.value.email,
                _authState.value.password
            )
        }
        saveUser(loginResult)
    }
    private suspend fun saveUser(userResource: Resource<FirebaseUser>) {
        when(userResource) {
            is Resource.Success -> {
                val userId = userResource.data?.uid
                userId?.let {
                    preferencesRepository.saveId(userId, DatastorePreferencesRepository.USER)
                }
                toggleDialog(DialogType.LOGIN)
            }
            is Resource.Error -> toastService.showToast(userResource.message ?: "Error signing in")
        }
    }

    fun setRestaurantId(restaurantId: String) = viewModelScope.launch {
        preferencesRepository.saveId(restaurantId, DatastorePreferencesRepository.RESTAURANT)
    }
    fun toggleDialog(type: DialogType) {
        _authState.update {
            when(type) {
                DialogType.SIGNUP -> it.copy(showSignUpDialog = !it.showSignUpDialog)
                DialogType.LOGIN -> {
                    if(!it.showLoginDialog) signInCredential()
                    it.copy(showLoginDialog = !it.showLoginDialog)
                }
                DialogType.RESET_PASSWORD -> it.copy(showResetPasswordDialog = !it.showResetPasswordDialog)
            }
        }
        clearFields()
    }
    fun resetPassword() = viewModelScope.launch {
        loginService.resetPassword(_authState.value.email)
        toggleDialog(DialogType.RESET_PASSWORD)
    }
    private fun clearFields() {
        _authState.update {
            it.copy(
                email = "",
                password = ""
            )
        }
    }
}