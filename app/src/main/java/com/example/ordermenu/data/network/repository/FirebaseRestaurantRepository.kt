package com.example.ordermenu.data.network.repository

import com.example.ordermenu.domain.model.restaurant.Restaurant
import com.example.ordermenu.domain.model.user.StaffUser
import com.example.ordermenu.domain.repository.RestaurantRepository
import com.example.ordermenu.domain.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRestaurantRepository @Inject constructor(
    private val firestore: FirebaseFirestore
): RestaurantRepository {
    private val restaurantCollectionRef = firestore.collection("restaurants")
    private val userCollectionRef = firestore.collection("users")
    override suspend fun getRestaurantByUserId(userId: String): Restaurant? {
        try {
            val snapshot = userCollectionRef.document(userId).get().await()
            if(!snapshot.exists()) {
                return createRestaurant(userId)
            } else {
                val existingRestaurantId = snapshot.toObject(StaffUser::class.java)?.restaurantId
                if(existingRestaurantId == null) {
                    return createRestaurant(userId)
                } else {
                    val existingRestaurantRef = restaurantCollectionRef.document(existingRestaurantId).get().await()
                    val existingRestaurant = existingRestaurantRef.toObject(Restaurant::class.java)
                    return existingRestaurant
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private suspend fun createRestaurant(userId: String): Restaurant? {
        try {
            val newUser = StaffUser(id = userId)
            userCollectionRef.document(userId).set(newUser).await()
            val newRestaurant = Restaurant(id = newUser.restaurantId)
            restaurantCollectionRef.document(newRestaurant.id).set(newRestaurant).await()
            return newRestaurant
        } catch(e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    override suspend fun getRestaurantById(id: String): Restaurant? {
        return try {
            val document = restaurantCollectionRef.document(id).get().await()
            document.toObject(Restaurant::class.java)
        } catch(e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun editRestaurant(restaurant: Restaurant) {
        try {
            restaurantCollectionRef.document(restaurant.id).set(restaurant).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}