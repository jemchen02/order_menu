package com.example.ordermenu.presentation.ui.menu

import android.net.Uri
import com.example.ordermenu.domain.model.category.DishCategory
import com.example.ordermenu.domain.model.dish.Dish
import com.example.ordermenu.domain.model.dish.DishEntry

data class MenuState(
    val dish: DishEntry = DishEntry(),
    val dishes: List<Dish> = emptyList(),
    val categories: List<DishCategory> = emptyList(),
    val category: DishCategory? = null,
    val newCategory: String = "",
    val uri: Uri? = null,
    val showDishDialog: Boolean = false,
    val showCategoryDialog: Boolean = false,
    val dishError: String? = null,
    val categoryError: String? = null
)