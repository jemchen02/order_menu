package com.example.ordermenu.presentation.ui.staff.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import com.example.ordermenu.domain.service.LoginService
import com.example.ordermenu.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginService: LoginService
): ViewModel() {
    fun signIn(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = viewModelScope.launch {
        when(val loginResult = loginService.signIn()) {
            is Resource.Success -> onSuccess()
            is Resource.Error -> onError(loginResult.message ?: "Unknown error signing in")
        }
    }
}