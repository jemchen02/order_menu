package com.example.ordermenu.domain.model.category

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class CategoryMappersKtTest {
    @Test
    fun `Empty string to category fails`() {
        val actual = "".toCategory("a")
        assertThat(actual.message).isEqualTo("Category name cannot be empty")
    }
    fun `Empty restaurant id to category fails`() {
        val actual = "Bread".toCategory("")
        assertThat(actual.message).isEqualTo("Restaurant id cannot be empty")
    }
    fun `Valid name and restaurant creates category`() {
        val actual = "Bread".toCategory("a")
        assertThat(actual.data?.name).isEqualTo("Bread")
        assertThat(actual.data?.restaurantId).isEqualTo("a")
    }
}