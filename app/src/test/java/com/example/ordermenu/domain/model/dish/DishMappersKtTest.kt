package com.example.ordermenu.domain.model.dish

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.ordermenu.util.dish
import com.example.ordermenu.util.dishEntry
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

class DishMappersKtTest {
    private lateinit var id: String
    @BeforeEach
    fun setUp() {
        id = UUID.randomUUID().toString()
    }
    @Test
    fun `create dish entry from dish succeeds`() {
        val actual = dish(id).toDishEntry()
        assertThat(actual).isEqualTo(dishEntry(id))
    }
    @Test
    fun `create dish with empty name fails`() {
        val emptyNameDish = dishEntry(id).copy(
            name = ""
        )
        val actual = emptyNameDish.toDish()
        assertThat(actual.message).isEqualTo("Name cannot be empty")
    }
    @Test
    fun `create dish with empty category fails`() {
        val emptyCategoryDish = dishEntry(id).copy(
            categoryId = ""
        )
        val actual = emptyCategoryDish.toDish()
        assertThat(actual.message).isEqualTo("Failure selecting category")
    }
    @Test
    fun `create dish with empty image fails`() {
        val emptyImageDish = dishEntry(id).copy(
            imageURL = ""
        )
        val actual = emptyImageDish.toDish()
        assertThat(actual.message).isEqualTo("You must choose an image")
    }
    @Test
    fun `create dish with negative price fails`() {
        val negativePriceDish = dishEntry(id).copy(
            price = "-1.5"
        )
        val actual = negativePriceDish.toDish()
        assertThat(actual.message).isEqualTo("Price cannot be negative")
    }
    @Test
    fun `create dish with invalid price fails`() {
        val invalidPriceDish = dishEntry(id).copy(
            price = "a.5"
        )
        val actual = invalidPriceDish.toDish()
        assertThat(actual.message).isEqualTo("Enter a valid price amount")
    }
    @Test
    fun `create dish with negative calories fails`() {
        val negativeCaloriesDish = dishEntry(id).copy(
            calories = "-15"
        )
        val actual = negativeCaloriesDish.toDish()
        assertThat(actual.message).isEqualTo("Calories cannot be negative")
    }
    @Test
    fun `create dish with invalid calories fails`() {
        val invalidCaloriesDish = dishEntry(id).copy(
            calories = "10a"
        )
        val actual = invalidCaloriesDish.toDish()
        assertThat(actual.message).isEqualTo("Enter a valid calories amount")
    }
    @Test
    fun `create valid dish from dish entry succeeds`() {
        val validDish = dishEntry(id)
        val actual = validDish.toDish()
        assertThat(actual.data).isEqualTo(dish(id))
    }
}