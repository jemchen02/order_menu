package com.example.ordermenu.presentation.ui.staff

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordermenu.data.network.repository.preferences.DatastorePreferencesRepository
import com.example.ordermenu.domain.model.restaurant.Restaurant
import com.example.ordermenu.domain.repository.preferences.PreferencesRepository
import com.example.ordermenu.domain.repository.restaurant.RestaurantRepository
import com.example.ordermenu.domain.service.LoginService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaffNavigationViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val restaurantRepository: RestaurantRepository
): ViewModel() {
    private val _navState = MutableStateFlow(StaffNavigationState())
    val navState = _navState.asStateFlow()

    init {
        viewModelScope.launch {
            preferencesRepository.getId(DatastorePreferencesRepository.USER).collect { id ->
                id?.let {
                    val restaurant = restaurantRepository.getRestaurantByUserId(id)
                    restaurant?.let {
                        _navState.update {
                            it.copy(
                                restaurant = restaurant
                            )
                        }
                    }
                }
            }
        }
    }

    fun toggleQrDialog() {
        _navState.update {
            it.copy(
                showQrDialog = !it.showQrDialog
            )
        }
    }

    fun logout() = viewModelScope.launch {
        preferencesRepository.clearId(DatastorePreferencesRepository.USER)
    }
}