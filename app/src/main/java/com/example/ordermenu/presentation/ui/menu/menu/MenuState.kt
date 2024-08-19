package com.example.ordermenu.presentation.ui.menu.menu

import android.net.Uri
import com.example.ordermenu.data.local.table.MenuItem
import java.util.UUID

data class MenuState(
    val menuItem: MenuItem = MenuItem(
        id = UUID.randomUUID().toString(),
        name = "Bread",
        categoryId = "",
        allergens = "",
        price = 5.0,
        calories = 50,
        imageURL = ""
    ),
    val uri: Uri? = null
)
