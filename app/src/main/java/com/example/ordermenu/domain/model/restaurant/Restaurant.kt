package com.example.ordermenu.domain.model.restaurant

import java.util.UUID

data class Restaurant(
    val id: String = UUID.randomUUID().toString(),
    val userId: String = "",
    val name: String = "Restaurant",
    val logoUrl: String = ""
)
