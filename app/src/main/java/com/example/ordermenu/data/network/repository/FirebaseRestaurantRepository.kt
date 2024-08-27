package com.example.ordermenu.data.network.repository

import com.example.ordermenu.domain.model.restaurant.Restaurant
import com.example.ordermenu.domain.repository.RestaurantRepository
import com.example.ordermenu.domain.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRestaurantRepository @Inject constructor(
    private val firestore: FirebaseFirestore
): RestaurantRepository {
    private val collectionRef = firestore.collection("restaurants")
    override suspend fun getRestaurantByUserId(userId: String): Restaurant? {
        try {
            val existingRestaurant = collectionRef.whereEqualTo("userId", userId).get().await()
            if(existingRestaurant.isEmpty) {
                val newRestaurant = Restaurant(userId = userId)
                collectionRef.document(newRestaurant.id).set(newRestaurant).await()
                return newRestaurant
            }
            val document = existingRestaurant.documents.first()
            return document.toObject(Restaurant::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    override suspend fun getRestaurantById(id: String): Restaurant? {
        return try {
            val document = collectionRef.document(id).get().await()
            document.toObject(Restaurant::class.java)
        } catch(e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun editRestaurant(restaurant: Restaurant) {
        try {
            collectionRef.document(restaurant.id).set(restaurant).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}