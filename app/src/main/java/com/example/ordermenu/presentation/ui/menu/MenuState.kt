package com.example.ordermenu.presentation.ui.menu

import android.net.Uri
import com.example.ordermenu.domain.model.dish.Dish
import com.example.ordermenu.domain.model.dish.DishEntry

data class MenuState(
    val dish: DishEntry = DishEntry(),
    val dishes: List<Dish> = emptyList(),
    val uri: Uri? = null,
    val showDialog: Boolean = false,
    val error: String? = null
)