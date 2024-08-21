package com.example.ordermenu.presentation.ui.menu

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ordermenu.presentation.ui.components.OrderMenuAppBar
import com.example.ordermenu.presentation.ui.menu.components.DishGrid
import com.example.ordermenu.presentation.ui.menu.components.EditDishDialog

@Composable
fun MenuPage() {
    val viewModel = hiltViewModel<MenuViewModel>()
    val menuState = viewModel.menuState.collectAsState().value
    val showDialog = menuState.showDialog
    val dishes = menuState.dishes
    Scaffold(
        topBar = {
            OrderMenuAppBar()
        },
        floatingActionButton = {
            FloatingActionButton(onClick = viewModel::toggleDialog) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Item"
                )
            }
        }
    ) { innerPadding ->
        DishGrid(
            itemList = dishes,
            modifier = Modifier.padding(innerPadding)
        )
        if (showDialog) {
            EditDishDialog(
                viewModel = viewModel
            )
        }
    }
}