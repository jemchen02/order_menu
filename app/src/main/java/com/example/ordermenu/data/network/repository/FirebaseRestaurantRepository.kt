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
    override suspend fun getRestaurantById(id: String): Resource<Restaurant> {
        return try {
            val existingRestaurant = collectionRef.whereEqualTo("id", id).get().await()
            if(existingRestaurant.isEmpty) {
                collectionRef.document(id).set(Restaurant(id = id)).await()
            }
            val document = collectionRef.document(id).get().await()
            Resource.Success(document.toObject(Restaurant::class.java))
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error getting restaurant")
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