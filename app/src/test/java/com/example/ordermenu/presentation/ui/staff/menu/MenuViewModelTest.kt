package com.example.ordermenu.presentation.ui.staff.menu

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNotEmpty
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import com.example.ordermenu.data.network.repository.preferences.DatastorePreferencesRepository
import com.example.ordermenu.domain.model.restaurant.Restaurant
import com.example.ordermenu.domain.repository.preferences.PreferencesRepository
import com.example.ordermenu.domain.repository.restaurant.CategoryRepository
import com.example.ordermenu.domain.repository.restaurant.DishRepository
import com.example.ordermenu.domain.repository.restaurant.ImageRepository
import com.example.ordermenu.domain.repository.restaurant.RestaurantRepository
import com.example.ordermenu.domain.service.ToastService
import com.example.ordermenu.fake.repository.preferences.FakePreferencesRepository
import com.example.ordermenu.fake.repository.restaurant.FakeCategoryRepository
import com.example.ordermenu.fake.repository.restaurant.FakeDishRepository
import com.example.ordermenu.fake.repository.restaurant.FakeImageRepository
import com.example.ordermenu.fake.repository.restaurant.FakeRestaurantRepository
import com.example.ordermenu.util.MainCoroutineExtension
import com.example.ordermenu.util.category
import com.example.ordermenu.util.dish
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.UUID

@ExtendWith(MainCoroutineExtension::class)
class MenuViewModelTest {
    private lateinit var viewModel: MenuViewModel
    private lateinit var imageRepository: ImageRepository
    private lateinit var dishRepository: DishRepository
    private lateinit var categoryRepository: CategoryRepository
    private lateinit var preferencesRepository: PreferencesRepository
    private lateinit var restaurantRepository: RestaurantRepository
    private lateinit var toastService: ToastService
    @BeforeEach
    fun setUp() = runTest{
        imageRepository = FakeImageRepository()
        dishRepository = FakeDishRepository()
        categoryRepository = FakeCategoryRepository()
        restaurantRepository = FakeRestaurantRepository()
        toastService = mockk(relaxed = true)

        preferencesRepository = FakePreferencesRepository()
        preferencesRepository.saveId("userid", DatastorePreferencesRepository.USER)
        viewModel = MenuViewModel(
            imageRepository,
            dishRepository,
            categoryRepository,
            preferencesRepository,
            restaurantRepository,
            toastService
        )
    }
    @Test
    fun `test initial empty viewmodel`() = runTest {
        viewModel.menuState.test {
            val emission1 = awaitItem()
            assertThat(emission1.restaurant).isNotNull()
            assertThat(emission1.newRestaurantName).isNotEmpty()
        }
    }
    @Test
    fun `test initial viewmodel with dish already added`() = runTest {
        val userId = preferencesRepository.getId(DatastorePreferencesRepository.USER).first() ?: ""
        val restaurant = restaurantRepository.getRestaurantByUserId(userId) ?: Restaurant()
        val category = category(restaurantId = restaurant.id)
        categoryRepository.addCategory(category)
        dishRepository.addDish(dish(UUID.randomUUID().toString()).copy(categoryId = category.id))
        viewModel = MenuViewModel(
            imageRepository,
            dishRepository,
            categoryRepository,
            preferencesRepository,
            restaurantRepository,
            toastService
        )
        viewModel.menuState.test {
            awaitItem()
            val emission2 = awaitItem()
            assertThat(emission2.restaurant).isNotNull()
            val emission3 = awaitItem()
            assertThat(emission3.categories).isNotEmpty()
            val emission4 = awaitItem()
            assertThat(emission4.category).isNotNull()
            val emission5 = awaitItem()
            assertThat(emission5.dishes).isNotEmpty()
        }
    }
    @Test
    fun `test update restaurant name flow`() = runTest{
        viewModel.menuState.test {
            awaitItem()

            viewModel.toggleRestaurantDialog()
            val emission2 = awaitItem()
            assertThat(emission2.showRestaurantDialog).isTrue()
            val newName = "a"

            viewModel.updateRestaurantName(newName)
            val emission3 = awaitItem()
            assertThat(emission3.newRestaurantName).isEqualTo(newName)

            viewModel.updateRestaurant()
            val emission4 = awaitItem()
            coVerify { toastService.showToast("Restaurant name changed to $newName") }
            assertThat(emission4.restaurant?.name).isEqualTo(newName)
            val emission5 = awaitItem()
            assertThat(emission5.showRestaurantDialog).isFalse()
        }
    }
    @Test
    fun `Test add category flow`() = runTest {
        viewModel.menuState.test {
            awaitItem()

            viewModel.toggleDeleteCategoryDialog()
            val emission2 = awaitItem()
            assertThat(emission2.showDeleteCategoryDialog).isTrue()
            val newCategory = "Breakfast"

            viewModel.updateNewCategory(newCategory)
            val emission3 = awaitItem()
            assertThat(emission3.newCategory).isEqualTo(newCategory)

            viewModel.addCategory()
            val emission4 = awaitItem()
            assertThat(emission4.newCategory).isEmpty()
            val emission5 = awaitItem()
            assertThat(emission5.categories.first { it.name == newCategory }).isNotNull()
            awaitItem()
            awaitItem()
            coVerify { toastService.showToast("Category: $newCategory created") }
        }
    }
}