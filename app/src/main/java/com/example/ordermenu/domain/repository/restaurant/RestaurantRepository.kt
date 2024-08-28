package com.example.ordermenu.domain.repository.restaurant

import com.example.ordermenu.domain.model.restaurant.Restaurant

interface RestaurantRepository {
    suspend fun getRestaurantByUserId(userId: String): Restaurant?

    suspend fun getRestaurantById(id: String): Restaurant?

    suspend fun editRestaurant(restaurant: Restaurant)
}