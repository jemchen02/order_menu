package com.example.ordermenu.domain.model.user

import java.util.UUID

data class StaffUser(
    val id: String = "",
    val restaurantId: String = UUID.randomUUID().toString()
)
