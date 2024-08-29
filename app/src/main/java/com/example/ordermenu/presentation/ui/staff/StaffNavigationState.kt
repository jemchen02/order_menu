package com.example.ordermenu.presentation.ui.staff

import com.example.ordermenu.domain.model.restaurant.Restaurant

data class StaffNavigationState(
    val restaurant: Restaurant? = null,
    val showQrDialog: Boolean = false
)
