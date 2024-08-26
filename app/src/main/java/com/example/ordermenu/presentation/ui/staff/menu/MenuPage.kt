package com.example.ordermenu.presentation.ui.staff.menu

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ordermenu.presentation.ui.components.OrderMenuAppBar
import com.example.ordermenu.presentation.ui.staff.menu.components.CategoryNavigationDrawer
import com.example.ordermenu.presentation.ui.staff.menu.components.dialog.DeleteCategoryDialog
import com.example.ordermenu.presentation.ui.staff.menu.components.dialog.DeleteDishDialog
import com.example.ordermenu.presentation.ui.staff.menu.components.dialog.EditCategoryDialog
import com.example.ordermenu.presentation.ui.staff.menu.components.dialog.EditDishDialog

@Composable
fun MenuPage(
    viewTickets: () -> Unit
) {
    val viewModel = hiltViewModel<MenuViewModel>()
    val menuState = viewModel.menuState.collectAsState().value
    Scaffold(
        topBar = {
            OrderMenuAppBar() {
                IconButton(onClick = viewTickets) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "View tickets",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
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
        if(menuState.showDeleteDishDialog) {
            DeleteDishDialog(viewModel = viewModel)
        }
    }
}