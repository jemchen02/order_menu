package com.example.ordermenu.data.fake

import com.example.ordermenu.domain.model.MenuItem

object MenuItemFakes {
    val menuItems = listOf(
        MenuItem(
            name = "Pancakes",
            ingredients = emptyList(),
            price = 4.50,
            calories = 300,
            imageURL = "https://images.pexels.com/photos/376464/pexels-photo-376464.jpeg?auto=compress&cs=tinysrgb&w=600"
        ),
        MenuItem(
            name = "Waffles",
            ingredients = emptyList(),
            price = 4.50,
            calories = 300,
            imageURL = "https://images.pexels.com/photos/378008/pexels-photo-378008.jpeg?auto=compress&cs=tinysrgb&w=600"
        ),
        MenuItem(
            name = "Milk",
            ingredients = emptyList(),
            price = 1.50,
            calories = 60,
            imageURL = "https://images.pexels.com/photos/1675976/pexels-photo-1675976.jpeg?auto=compress&cs=tinysrgb&w=600"
        ),
        MenuItem(
            name = "Scrambled egg",
            ingredients = emptyList(),
            price = 4.50,
            calories = 300,
            imageURL = "https://images.pexels.com/photos/19257475/pexels-photo-19257475/free-photo-of-top-view-of-breakfast-served-on-a-table.jpeg?auto=compress&cs=tinysrgb&w=600"
        ),
    )
}