package com.example.ordermenu.presentation.ui.customer.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordermenu.data.network.repository.preferences.DatastorePreferencesRepository
import com.example.ordermenu.domain.model.category.DishCategory
import com.example.ordermenu.domain.model.dish.Dish
import com.example.ordermenu.domain.model.order.Order
import com.example.ordermenu.domain.repository.preferences.PreferencesRepository
import com.example.ordermenu.domain.repository.restaurant.CategoryRepository
import com.example.ordermenu.domain.repository.restaurant.DishRepository
import com.example.ordermenu.domain.repository.restaurant.OrderRepository
import com.example.ordermenu.domain.repository.restaurant.RestaurantRepository
import com.example.ordermenu.domain.service.ToastService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerMenuViewModel @Inject constructor(
    private val dishRepository: DishRepository,
    private val categoryRepository: CategoryRepository,
    private val orderRepository: OrderRepository,
    private val preferencesRepository: PreferencesRepository,
    private val restaurantRepository: RestaurantRepository,
    private val toastService: ToastService
) : ViewModel(){
    private val _menuState = MutableStateFlow(CustomerMenuState())
    val menuState = _menuState.asStateFlow()
    init {
        viewModelScope.launch {
            preferencesRepository.getId(DatastorePreferencesRepository.RESTAURANT).collect { id ->
                id?.let {
                    val restaurant = restaurantRepository.getRestaurantById(id)
                    if(restaurant != null) {
                        _menuState.update {
                            it.copy(
                                restaurant = restaurant,
                                order = it.order.copy(
                                    restaurantId = restaurant.id
                                )
                            )
                        }
                        getAllCategories()
                        getDishesInCategory()
                    }
                }
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

    fun addDish(dish: Dish) {
        _menuState.update {
            val updatedOrder = it.order.copy()
            updatedOrder.addDish(dish)
            it.copy(
                order = updatedOrder
            )
        }
        toastService.showToast("Added ${dish.name} to order")

    }

    fun removeDish(dish: Dish) {
        _menuState.update {
            val updatedOrder = it.order.copy()
            updatedOrder.removeDish(dish)
            it.copy(
                order = updatedOrder
            )
        }
        toastService.showToast("Removed ${dish.name} from order")
    }

    fun updateTable(table: String) {
        _menuState.update {
            it.copy(
                order = it.order.copy(
                    table = table
                )
            )
        }
    }

    fun updateInstructions(instructions: String) {
        _menuState.update {
            it.copy(
                order = it.order.copy(
                    additionalInstructions = instructions
                )
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

    fun submitOrder() = viewModelScope.launch {
        orderRepository.addOrder(_menuState.value.order)
        _menuState.update {
            it.copy(
                order = Order(
                    restaurantId = _menuState.value.restaurant?.id ?: ""
                )
            )
        }
        toggleOrder()
        toastService.showToast("Order sent")
    }

    fun exitRestaurant() = viewModelScope.launch {
        preferencesRepository.clearId(DatastorePreferencesRepository.RESTAURANT)
        toastService.showToast("Exited restaurant")
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
        val restaurantId = _menuState.value.restaurant?.id ?: ""
        _menuState.update {
            it.copy(
                categories = categoryRepository.getAllCategoriesByRestaurantId(restaurantId)
            )
        }
        if(_menuState.value.categories.isNotEmpty()) {
            selectCategory(_menuState.value.categories.first())
        }
    }

}