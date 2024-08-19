package com.example.ordermenu.data.fake

import com.example.ordermenu.data.local.table.Category
import java.util.UUID

object CategoryFakes {
    val categories = listOf(
        Category(
            id = UUID.randomUUID().toString(),
            name = "Breakfast"
        )
    )
}