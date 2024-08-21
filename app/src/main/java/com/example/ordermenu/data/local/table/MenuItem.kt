package com.example.ordermenu.data.local.table

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "menu_items",
//    foreignKeys = [
//        ForeignKey(
//            entity = Category::class,
//            parentColumns = ["id"],
//            childColumns = ["categoryId"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ],
    indices = [Index(value = ["categoryId"])]
)
data class MenuItem(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val categoryId: String = "",
    val allergens: String = "",
    val price: Double = 0.0,
    val calories: Int = 0,
    val imageURL: String = ""
)

enum class MenuField {
    NAME,
    CATEGORY_ID,
    ALLERGENS,
    PRICE,
    CALORIES,
    IMAGE_URL
}