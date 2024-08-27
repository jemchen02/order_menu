package com.example.ordermenu.data.network.repository

import com.example.ordermenu.domain.model.category.DishCategory
import com.example.ordermenu.domain.repository.CategoryRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseCategoryRepository @Inject constructor(
    private val firestore: FirebaseFirestore
): CategoryRepository {
    private val collectionRef = firestore.collection("categories")
    override suspend fun getAllCategories(): List<DishCategory> {
        return try {
            val snapshot = collectionRef.get().await()
            snapshot.documents.mapNotNull {
                it.toObject(DishCategory::class.java)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun addCategory(category: DishCategory) {
        try {
            collectionRef.document(category.id).set(category).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun deleteCategory(id: String) {
        try {
            collectionRef.document(id).delete().await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}