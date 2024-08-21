package com.example.ordermenu.presentation.ui.menu.menu

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
import com.example.ordermenu.presentation.ui.menu.components.EditMenuItemDialog
import com.example.ordermenu.presentation.ui.menu.components.MenuItemGrid

@Composable
fun MenuPage() {
    val viewModel = hiltViewModel<MenuViewModel>()
    val menuState = viewModel.menuState.collectAsState().value
    val showDialog = menuState.showDialog
    val menuItems = viewModel.getAllMenuItems().collectAsState(emptyList()).value
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
        MenuItemGrid(
            itemList = menuItems,
            modifier = Modifier.padding(innerPadding)
        )
        if (showDialog) {
            EditMenuItemDialog(
                viewModel = viewModel
            )
        }
    }
}