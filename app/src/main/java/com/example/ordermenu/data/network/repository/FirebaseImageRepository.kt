package com.example.ordermenu.data.network.repository

import android.net.Uri
import com.example.ordermenu.domain.repository.ImageRepository
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID

class FirebaseImageRepository(): ImageRepository {
    private val storageRef = FirebaseStorage.getInstance().reference
    override suspend fun uploadImage(uri: Uri): String? {
        return try {
            val imageRef = storageRef.child("images/${UUID.randomUUID()}.jpg")
            // Upload the image
            imageRef.putFile(uri).await()
            // Get the download URL
            val downloadUrl = imageRef.downloadUrl.await()
            downloadUrl.toString()
        } catch (e: Exception) {
            // Handle exceptions and return null in case of failure
            e.printStackTrace()
            null
        }
    }
}