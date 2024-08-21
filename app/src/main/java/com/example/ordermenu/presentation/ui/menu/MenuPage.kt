package com.example.ordermenu.presentation.ui.menu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ordermenu.presentation.ui.components.OrderMenuAppBar
import com.example.ordermenu.presentation.ui.menu.components.CategoryNavigationDrawer
import com.example.ordermenu.presentation.ui.menu.components.dialog.EditCategoryDialog
import com.example.ordermenu.presentation.ui.menu.components.dialog.EditDishDialog

@Composable
fun MenuPage() {
    val viewModel = hiltViewModel<MenuViewModel>()
    val menuState = viewModel.menuState.collectAsState().value
    val showDishDialog = menuState.showDishDialog
    val showCategoryDialog = menuState.showCategoryDialog
    Scaffold(
        topBar = {
            OrderMenuAppBar()
        },
        floatingActionButton = {
            FloatingActionButton(onClick = viewModel::toggleDishDialog) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Item"
                )
            }
        }
    ) { innerPadding ->
        CategoryNavigationDrawer(
            viewModel = viewModel,
            innerPadding = innerPadding
        )
        if (showDishDialog) {
            EditDishDialog(viewModel = viewModel)
        }
        if (showCategoryDialog) {
            EditCategoryDialog(viewModel = viewModel)
        }
    }
}