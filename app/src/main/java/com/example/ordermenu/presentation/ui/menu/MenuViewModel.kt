package com.example.ordermenu.presentation.ui.menu

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordermenu.domain.model.dish.DishFields
import com.example.ordermenu.domain.model.dish.toDish
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
    private val dishRepository: DishRepository
): ViewModel(){
    private val _menuState = MutableStateFlow(MenuState())
    val menuState = _menuState.asStateFlow()
    init {
        viewModelScope.launch {
            getAllDishes()
        }
    }
    private suspend fun getAllDishes() {
        _menuState.update {
            it.copy(
                dishes = dishRepository.getAllDishes()
            )
        }
    }

    fun toggleDialog() {
        _menuState.update {
            it.copy(
                showDialog = !it.showDialog
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
        when(val newDishResource = _menuState.value.dish.toDish()) {
            is Resource.Success -> newDishResource.data?.let {
                dishRepository.addDish(it)
                getAllDishes()
                toggleDialog()
            }
            else -> _menuState.update {
                it.copy(
                    error = newDishResource.message
                )
            }
        }
    }
}