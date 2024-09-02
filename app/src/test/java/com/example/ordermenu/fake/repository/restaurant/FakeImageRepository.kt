package com.example.ordermenu.fake.repository.restaurant

import android.net.Uri
import com.example.ordermenu.domain.repository.restaurant.ImageRepository

class FakeImageRepository: ImageRepository {
    override suspend fun uploadImage(uri: Uri): String {
        return uri.toString()
    }
}