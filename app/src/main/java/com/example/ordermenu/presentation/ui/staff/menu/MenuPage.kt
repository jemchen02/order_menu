package com.example.ordermenu.presentation.ui.staff.menu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ordermenu.presentation.ui.common.OrderMenuAppBar
import com.example.ordermenu.presentation.ui.staff.menu.components.CategoryNavigationDrawer
import com.example.ordermenu.presentation.ui.staff.menu.components.dialog.DeleteCategoryDialog
import com.example.ordermenu.presentation.ui.staff.menu.components.dialog.DeleteDishDialog
import com.example.ordermenu.presentation.ui.staff.menu.components.dialog.EditCategoryDialog
import com.example.ordermenu.presentation.ui.staff.menu.components.dialog.EditDishDialog
import com.example.ordermenu.presentation.ui.staff.menu.components.dialog.EditRestaurantDialog

@Composable
fun MenuPage(
    viewTickets: () -> Unit,
    onNavigationClick: () -> Unit
) {
    val viewModel = hiltViewModel<MenuViewModel>()
    val menuState = viewModel.menuState.collectAsState().value

    Scaffold(
        topBar = {
            OrderMenuAppBar(
                title = menuState.restaurant?.name ?: "Order Menu",
                onNameClick = viewModel::toggleRestaurantDialog,
                onNavigationClick = onNavigationClick
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = viewModel::startAddDish) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Dish"
                )
            }
        }
    ) { innerPadding ->
        CategoryNavigationDrawer(
            viewModel = viewModel,
            innerPadding = innerPadding
        )
        if (menuState.showDishDialog) {
            EditDishDialog(viewModel = viewModel)
        }
        if (menuState.showCategoryDialog) {
            EditCategoryDialog(viewModel = viewModel)
        }
        if (menuState.showDeleteCategoryDialog) {
            DeleteCategoryDialog(viewModel = viewModel)
        }
        if (menuState.showDeleteDishDialog) {
            DeleteDishDialog(viewModel = viewModel)
        }
        if (menuState.showRestaurantDialog) {
            EditRestaurantDialog(viewModel = viewModel)
        }
    }
}