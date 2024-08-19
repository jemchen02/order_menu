package com.example.ordermenu.data.local.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category (
    @PrimaryKey
    val id: String,
    val name: String
)