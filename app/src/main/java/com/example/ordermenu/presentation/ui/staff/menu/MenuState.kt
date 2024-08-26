package com.example.ordermenu.presentation.ui.staff.menu

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
    val editingCategory: DishCategory? = null,

    val uri: Uri? = null,

    val showDishDialog: Boolean = false,
    val showDeleteDishDialog: Boolean = false,
    val showCategoryDialog: Boolean = false,
    val showDeleteCategoryDialog: Boolean = false,

    val dishError: String? = null,
    val categoryError: String? = null
)
enum class StaffDialogType {
    EditDish, EditCategory, DeleteCategory, DeleteDish
}