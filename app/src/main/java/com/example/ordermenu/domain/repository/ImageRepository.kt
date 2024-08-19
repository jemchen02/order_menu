package com.example.ordermenu.domain.repository

import android.net.Uri

interface ImageRepository {
    suspend fun uploadImage(uri: Uri): String?
}