package com.example.ordermenu.fake.repository.restaurant

import com.example.ordermenu.domain.model.restaurant.Restaurant
import com.example.ordermenu.domain.model.user.StaffUser
import com.example.ordermenu.domain.repository.restaurant.RestaurantRepository

class FakeRestaurantRepository: RestaurantRepository {
    private val _restaurants = mutableListOf<Restaurant>()
    private val _users = mutableListOf<StaffUser>()
    override suspend fun getRestaurantByUserId(userId: String): Restaurant? {
        val userIndex = _users.indexOfFirst { it.id == userId }
        if(userIndex == -1) {
            val newUser = StaffUser(id = userId)
            _users.add(newUser)
            val newRestaurant = Restaurant(id = newUser.restaurantId)
            _restaurants.add(newRestaurant)
            return newRestaurant
        }
        return _restaurants.first { it.id == _users[userIndex].restaurantId }
    }

    override suspend fun getRestaurantById(id: String): Restaurant? {
        return _restaurants.first { it.id == id }
    }

    override suspend fun editRestaurant(restaurant: Restaurant) {
        val index = _restaurants.indexOfFirst { it.id == restaurant.id }
        _restaurants[index] = restaurant
    }
}