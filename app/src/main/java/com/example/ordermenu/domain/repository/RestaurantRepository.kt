package com.example.ordermenu.domain.repository

import com.example.ordermenu.domain.model.restaurant.Restaurant
import com.example.ordermenu.domain.util.Resource

interface RestaurantRepository {
    suspend fun getRestaurantById(id: String): Resource<Restaurant>

    suspend fun editRestaurant(restaurant: Restaurant)
}