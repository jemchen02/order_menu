package com.example.ordermenu.domain.service

import com.example.ordermenu.domain.util.Resource
import com.google.firebase.auth.FirebaseUser

interface LoginService {
    suspend fun signUpPassword(email: String, password: String)
    suspend fun signInPassword(email: String, password: String): Resource<FirebaseUser>
    suspend fun signIn(): Resource<FirebaseUser>
    suspend fun resetPassword(email: String)
    suspend fun savePassword(email: String, password: String)
    fun signOut()
    fun getCurrentUser(): FirebaseUser?
}