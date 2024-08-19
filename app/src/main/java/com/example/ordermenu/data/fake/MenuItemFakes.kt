package com.example.ordermenu.data.fake

import com.example.ordermenu.domain.Allergens
import com.example.ordermenu.data.local.table.MenuItem
import java.util.UUID

object MenuItemFakes {
    val menuItems = listOf(
        MenuItem(
            id = UUID.randomUUID().toString(),
            name = "Pancakes",
            categoryId = CategoryFakes.categories.first().id,
            allergens = listOf(
                Allergens.Eggs.name,
                Allergens.Wheat.name,
                Allergens.Milk.name
            ).toString(),
            price = 4.50,
            calories = 300,
            imageURL = "https://images.pexels.com/photos/376464/pexels-photo-376464.jpeg?auto=compress&cs=tinysrgb&w=600"
        ),
        MenuItem(
            id = UUID.randomUUID().toString(),
            name = "Waffles",
            categoryId = CategoryFakes.categories.first().id,
            allergens = listOf(
                Allergens.Eggs.name,
                Allergens.Wheat.name,
                Allergens.Milk.name
            ).toString(),
            price = 4.50,
            calories = 300,
            imageURL = "https://images.pexels.com/photos/378008/pexels-photo-378008.jpeg?auto=compress&cs=tinysrgb&w=600"
        ),
        MenuItem(
            id = UUID.randomUUID().toString(),
            name = "Milk",
            categoryId = CategoryFakes.categories.first().id,
            allergens = listOf(
                Allergens.Milk.name
            ).toString(),
            price = 1.50,
            calories = 60,
            imageURL = "https://images.pexels.com/photos/1675976/pexels-photo-1675976.jpeg?auto=compress&cs=tinysrgb&w=600"
        ),
        MenuItem(
            id = UUID.randomUUID().toString(),
            name = "Scrambled egg",
            categoryId = CategoryFakes.categories.first().id,
            allergens = listOf(
                Allergens.Eggs.name,
                Allergens.Milk.name
            ).toString(),
            price = 4.50,
            calories = 300,
            imageURL = "https://images.pexels.com/photos/19257475/pexels-photo-19257475/free-photo-of-top-view-of-breakfast-served-on-a-table.jpeg?auto=compress&cs=tinysrgb&w=600"
        ),
    )
}