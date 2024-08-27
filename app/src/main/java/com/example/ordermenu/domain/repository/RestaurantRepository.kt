package com.example.ordermenu.domain.repository

import com.example.ordermenu.domain.model.restaurant.Restaurant
import com.example.ordermenu.domain.util.Resource

interface RestaurantRepository {
    suspend fun getRestaurantByUserId(userId: String): Restaurant?

    suspend fun getRestaurantById(id: String): Restaurant?

    suspend fun editRestaurant(restaurant: Restaurant)
}