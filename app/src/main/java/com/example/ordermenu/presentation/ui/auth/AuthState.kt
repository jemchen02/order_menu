package com.example.ordermenu.presentation.ui.auth

data class AuthState(
    val email: String = "",
    val password: String = "",

    val showSignUpDialog: Boolean = false,
    val showLoginDialog: Boolean = false
)
enum class AuthFieldType {
    EMAIL, PASSWORD
}
enum class DialogType {
    SIGNUP, LOGIN
}