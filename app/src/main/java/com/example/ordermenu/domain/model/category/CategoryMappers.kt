package com.example.ordermenu.domain.model.category

import com.example.ordermenu.domain.util.Resource

fun String.toCategory(): Resource<DishCategory>{
    if(this.isEmpty()) return Resource.Error("Category name cannot be empty")
    return Resource.Success(
        DishCategory(name = this)
    )
}