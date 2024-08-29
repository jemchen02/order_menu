package com.example.ordermenu.domain.service

import com.example.ordermenu.domain.util.Resource
import com.google.firebase.auth.FirebaseUser

interface LoginService {
    suspend fun signIn(): Resource<FirebaseUser>
    fun signOut()
    fun getCurrentUser(): FirebaseUser?
}