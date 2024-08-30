package com.example.ordermenu.domain.model.dish

import com.example.ordermenu.domain.util.Resource
import com.google.rpc.context.AttributeContext
import java.util.UUID

fun Dish.toDishEntry(): DishEntry = DishEntry(
    id = id,
    name = name,
    categoryId = categoryId,
    allergens = allergens,
    price = price.toString(),
    calories = calories.toString(),
    imageURL = imageURL
)
fun DishEntry.toDish(): Resource<Dish> {
    if(name.isEmpty()) return Resource.Error("Name cannot be empty")
    if(categoryId.isEmpty()) return Resource.Error("Failure selecting category")
    if(imageURL.isEmpty()) return Resource.Error("You must choose an image")
    try {
        val convertedPrice = price.toDouble()
        if(convertedPrice < 0) return Resource.Error("Price cannot be negative")
    } catch(e: Exception) {
        return Resource.Error("Enter a valid price amount")
    }
    try {
        val convertedCalories = calories.toInt()
        if(convertedCalories < 0) return Resource.Error("Calories cannot be negative")
    } catch(e: Exception) {
        return Resource.Error("Enter a valid calories amount")
    }
    return try {
        Resource.Success(
            data = Dish(
                id = id.ifEmpty { UUID.randomUUID().toString() },
                name = name,
                categoryId = categoryId,
                allergens = allergens,
                price = price.toDouble(),
                calories = calories.toInt(),
                imageURL = imageURL
            )
        )
    } catch (e: Exception) {
        Resource.Error("Unknown exception occurred")
    }
}