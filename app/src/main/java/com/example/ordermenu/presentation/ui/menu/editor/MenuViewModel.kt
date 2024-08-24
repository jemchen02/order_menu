package com.example.ordermenu.presentation.ui.menu.editor

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordermenu.domain.model.category.DishCategory
import com.example.ordermenu.domain.model.category.toCategory
import com.example.ordermenu.domain.model.dish.DishEntry
import com.example.ordermenu.domain.model.dish.DishFields
import com.example.ordermenu.domain.model.dish.toDish
import com.example.ordermenu.domain.repository.CategoryRepository
import com.example.ordermenu.domain.repository.DishRepository
import com.example.ordermenu.domain.repository.ImageRepository
import com.example.ordermenu.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
    private val dishRepository: DishRepository,
    private val categoryRepository: CategoryRepository
): ViewModel(){
    private val _menuState = MutableStateFlow(MenuState())
    val menuState = _menuState.asStateFlow()
    init {
        viewModelScope.launch {
            getAllCategories()
            getDishesInCategory()
        }
    }
    private suspend fun getDishesInCategory() {
        _menuState.value.category?.let{ category->
            _menuState.update {
                it.copy(
                    dishes = dishRepository.getDishesByCategoryId(category.id)
                )
            }
        }
    }
    private suspend fun getAllCategories() {
        _menuState.update {
            it.copy(
                categories = categoryRepository.getAllCategories()
            )
        }
        if(_menuState.value.categories.isNotEmpty()) {
            selectCategory(_menuState.value.categories.first())
        }
    }

    fun toggleDishDialog() {
        _menuState.update {
            it.copy(
                showDishDialog = !it.showDishDialog
            )
        }
    }
    fun toggleCategoryDialog() {
        _menuState.update {
            it.copy(
                showCategoryDialog = !it.showCategoryDialog
            )
        }
    }
    fun updateField(field: DishFields, newValue: String) {
        _menuState.update {
            it.copy(
                dish = when(field) {
                    DishFields.NAME -> it.dish.copy(name = newValue)
                    DishFields.PRICE -> it.dish.copy(price = newValue)
                    DishFields.IMAGE_URL -> it.dish.copy(imageURL = newValue)
                    DishFields.CALORIES -> it.dish.copy(calories = newValue)
                    DishFields.CATEGORY_ID -> it.dish.copy(categoryId = newValue)
                    DishFields.ALLERGENS -> it.dish.copy(allergens = newValue)
                }
            )
        }
    }
    fun updateNewCategory(newValue: String) {
        _menuState.update {
            it.copy(
                newCategory = newValue
            )
        }
    }
    fun uploadImage(uri: Uri?) {
        viewModelScope.launch {
            uri?.let {
                val downloadUrl = imageRepository.uploadImage(uri)
                downloadUrl?.let {
                    updateField(DishFields.IMAGE_URL, it)
                }
            }
        }
    }

    fun addDish() = viewModelScope.launch{
        _menuState.update {
            it.copy(
                dish = it.dish.copy(
                    categoryId = it.category?.id ?: ""
                )
            )
        }
        when(val newDishResource = _menuState.value.dish.toDish()) {
            is Resource.Success -> newDishResource.data?.let {
                dishRepository.addDish(it)
                getDishesInCategory()
                toggleDishDialog()
                _menuState.update { state->
                    state.copy(
                        dish = DishEntry()
                    )
                }
            }
            else -> _menuState.update {
                it.copy(
                    dishError = newDishResource.message
                )
            }
        }
    }

    fun addCategory() = viewModelScope.launch {
        when(val newCategoryResource = _menuState.value.newCategory.toCategory()) {
            is Resource.Success -> newCategoryResource.data?.let {
                categoryRepository.addCategory(it)
                getAllCategories()
                toggleCategoryDialog()
            }
            else -> _menuState.update {
                it.copy(
                    categoryError = newCategoryResource.message
                )
            }
        }
    }

    fun selectCategory(category: DishCategory) = viewModelScope.launch{
        _menuState.update {
            it.copy(
                category = category
            )
        }
        getDishesInCategory()
    }
}