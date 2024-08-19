package com.example.ordermenu.data.local.table

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

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
    val id: String,
    val name: String,
    val categoryId: String,
    val allergens: String,
    val price: Double,
    val calories: Int,
    val imageURL: String
)
