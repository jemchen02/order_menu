package com.example.ordermenu.presentation.ui.menu.customer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordermenu.domain.model.category.DishCategory
import com.example.ordermenu.domain.model.dish.Dish
import com.example.ordermenu.domain.repository.CategoryRepository
import com.example.ordermenu.domain.repository.DishRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerMenuViewModel @Inject constructor(
    private val dishRepository: DishRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel(){
    private val _menuState = MutableStateFlow(CustomerMenuState())
    val menuState = _menuState.asStateFlow()
    init {
        viewModelScope.launch {
            getAllCategories()
            getDishesInCategory()
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

    fun addDish(dish: Dish) {
        _menuState.update {
            val updatedOrder = it.order.copy()
            updatedOrder.addDish(dish)
            it.copy(
                order = updatedOrder
            )
        }
    }

    fun removeDish(dish: Dish) {
        _menuState.update {
            val updatedOrder = it.order.copy()
            updatedOrder.removeDish(dish)
            it.copy(
                order = updatedOrder
            )
        }
    }

    fun toggleOrder() {
        _menuState.update {
            it.copy(
                showOrder = !it.showOrder
            )
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
}