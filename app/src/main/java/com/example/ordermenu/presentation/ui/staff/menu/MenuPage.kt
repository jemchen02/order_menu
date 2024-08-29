package com.example.ordermenu.presentation.ui.staff.menu

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ordermenu.presentation.ui.components.OrderMenuAppBar
import com.example.ordermenu.presentation.ui.staff.components.StaffModalDrawer
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