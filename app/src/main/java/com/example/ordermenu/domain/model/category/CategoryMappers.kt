package com.example.ordermenu.domain.model.category

import com.example.ordermenu.domain.util.Resource

fun String.toCategory(restaurantId: String): Resource<DishCategory>{
    if(this.isEmpty()) return Resource.Error("Category name cannot be empty")
    if(restaurantId.isEmpty()) return Resource.Error("Restaurant id cannot be empty")
    return Resource.Success(
        DishCategory(
            name = this,
            restaurantId = restaurantId
        )
    )
}