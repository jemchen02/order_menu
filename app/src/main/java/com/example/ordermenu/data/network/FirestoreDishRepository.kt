package com.example.ordermenu.data.network

import android.util.Log
import com.example.ordermenu.domain.model.dish.Dish
import com.example.ordermenu.domain.repository.DishRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreDishRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : DishRepository {
    private val collectionRef = firestore.collection("dishes")
    override suspend fun getAllDishes(): List<Dish> {
        return try {
            val snapshot = collectionRef.get().await()
            snapshot.documents.mapNotNull {
                it.toObject(Dish::class.java)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getDishesByCategoryId(categoryId: String): List<Dish> {
        return try {
            val snapshot = collectionRef.get().await()
            snapshot.documents.mapNotNull {
                it.toObject(Dish::class.java)
            }.filter {
                it.categoryId == categoryId
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getDishById(id: String): Dish? {
        return try {
            val document = collectionRef.document(id).get().await()
            document.toObject(Dish::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun addDish(dish: Dish) {
        try {
            collectionRef.document(dish.id).set(dish).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun updateDish(dish: Dish) {
        try {
            collectionRef.document(dish.id).set(dish).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun deleteDish(id: String) {
        try {
            collectionRef.document(id).delete().await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}