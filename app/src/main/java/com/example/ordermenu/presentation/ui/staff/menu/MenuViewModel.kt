package com.example.ordermenu.presentation.ui.staff.menu

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordermenu.data.network.repository.preferences.DatastorePreferencesRepository
import com.example.ordermenu.domain.model.category.DishCategory
import com.example.ordermenu.domain.model.category.toCategory
import com.example.ordermenu.domain.model.dish.Dish
import com.example.ordermenu.domain.model.dish.DishEntry
import com.example.ordermenu.domain.model.dish.toDish
import com.example.ordermenu.domain.model.dish.toDishEntry
import com.example.ordermenu.domain.repository.restaurant.CategoryRepository
import com.example.ordermenu.domain.repository.restaurant.DishRepository
import com.example.ordermenu.domain.repository.restaurant.ImageRepository
import com.example.ordermenu.domain.repository.preferences.PreferencesRepository
import com.example.ordermenu.domain.repository.restaurant.RestaurantRepository
import com.example.ordermenu.domain.service.LoginService
import com.example.ordermenu.domain.service.ToastService
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
    private val categoryRepository: CategoryRepository,
    private val preferencesRepository: PreferencesRepository,
    private val restaurantRepository: RestaurantRepository,
    private val toastService: ToastService
): ViewModel(){
    private val _menuState = MutableStateFlow(MenuState())
    val menuState = _menuState.asStateFlow()
    init {
        viewModelScope.launch {
            preferencesRepository.getId(DatastorePreferencesRepository.USER).collect { id ->
                id?.let {
                    val restaurant = restaurantRepository.getRestaurantByUserId(id)
                    if(restaurant != null) {
                        _menuState.update {
                            it.copy(
                                restaurant = restaurant,
                                newRestaurantName = restaurant.name
                            )
                        }
                        getAllCategories()
                        getDishesInCategory()
                    }
                }
            }
        }
    }
    private suspend fun refreshRestaurant() {
        val restaurantId = _menuState.value.restaurant?.id
        restaurantId?.let {
            val restaurant = restaurantRepository.getRestaurantById(restaurantId)
            _menuState.update {
                it.copy(
                    restaurant = restaurant
                )
            }
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

    fun toggleDishDialog() = toggleDialog(StaffDialogType.EditDish)
    fun toggleDeleteDishDialog() = toggleDialog(StaffDialogType.DeleteDish)
    fun toggleCategoryDialog() = toggleDialog(StaffDialogType.EditCategory)
    fun toggleDeleteCategoryDialog() = toggleDialog(StaffDialogType.DeleteCategory)
    fun toggleRestaurantDialog() = toggleDialog(StaffDialogType.EditRestaurant)

    private fun toggleDialog(type: StaffDialogType) {
        _menuState.update {
            when(type) {
                StaffDialogType.EditDish -> it.copy(showDishDialog = !it.showDishDialog)
                StaffDialogType.DeleteDish -> it.copy(showDeleteDishDialog = !it.showDeleteDishDialog)
                StaffDialogType.EditCategory -> it.copy(showCategoryDialog = !it.showCategoryDialog)
                StaffDialogType.DeleteCategory -> it.copy(showDeleteCategoryDialog = !it.showDeleteCategoryDialog)
                StaffDialogType.EditRestaurant -> it.copy(showRestaurantDialog = !it.showRestaurantDialog)
            }
        }
    }

    fun updateField(field: DishFields, newValue: String) {
        _menuState.update {
            it.copy(
                dish = when(field) {
                    DishFields.DISH_NAME -> it.dish.copy(name = newValue)
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
    fun updateRestaurantName(newName: String) {
        _menuState.update {
            it.copy(
                newRestaurantName = newName
            )
        }
    }
    fun uploadImage(uri: Uri?) {
        viewModelScope.launch {
            uri?.let {
                val downloadUrl = imageRepository.uploadImage(uri)
                downloadUrl?.let {
                    updateField(DishFields.IMAGE_URL, it)
                    toastService.showToast("Image uploaded")
                }
            }
        }
    }

    fun startAddDish() {
        _menuState.update {
            it.copy(
                dish = DishEntry()
            )
        }
        toggleDishDialog()
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
                toastService.showToast("Dish: ${it.name} created")
            }
            else -> _menuState.update {
                it.copy(
                    dishError = newDishResource.message
                )
            }
        }
    }

    fun selectDish(dish: Dish) {
        _menuState.update {
            it.copy(
                dish = dish.toDishEntry()
            )
        }
        toggleDishDialog()
    }

    fun deleteDish() = viewModelScope.launch {
        val dish = _menuState.value.dish
        dishRepository.deleteDish(dish.id)
        getDishesInCategory()
        toggleDeleteDishDialog()
        toggleDishDialog()
        toastService.showToast("Dish: ${dish.name} deleted")
    }

    fun addCategory() = viewModelScope.launch {
        val restaurantId = _menuState.value.restaurant?.id ?: ""
        when(val newCategoryResource = _menuState.value.newCategory.toCategory(restaurantId)) {
            is Resource.Success -> newCategoryResource.data?.let {
                var newCategory = it
                val editingCategory = _menuState.value.editingCategory
                editingCategory?.let {
                    newCategory = newCategory.copy(
                        id = editingCategory.id
                    )
                }
                _menuState.update { state->
                    state.copy(
                        newCategory = ""
                    )
                }
                categoryRepository.addCategory(newCategory)
                getAllCategories()
                toggleCategoryDialog()
                toastService.showToast("Category: ${it.name} created")
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

    fun selectEditCategory(category: DishCategory) {
        toggleCategoryDialog()
        _menuState.update {
            it.copy(
                editingCategory = category,
                newCategory = category.name
            )
        }
    }

    fun selectDeleteCategory(category: DishCategory) {
        toggleDeleteCategoryDialog()
        _menuState.update {
            it.copy(
                editingCategory = category
            )
        }
    }

    fun deleteCategory() = viewModelScope.launch {
        val category = _menuState.value.editingCategory
        category?.let {
            categoryRepository.deleteCategory(category.id)
            getAllCategories()
            toastService.showToast("Category: ${category.name} deleted")
        }
        toggleDeleteCategoryDialog()
    }

    fun updateRestaurant() = viewModelScope.launch {
        val newRestaurant = _menuState.value.restaurant?.copy(
            name = _menuState.value.newRestaurantName
        )
        newRestaurant?.let {
            restaurantRepository.editRestaurant(newRestaurant)
            toastService.showToast("Restaurant name changed to ${it.name}")
        }
        refreshRestaurant()
        toggleRestaurantDialog()
    }
}