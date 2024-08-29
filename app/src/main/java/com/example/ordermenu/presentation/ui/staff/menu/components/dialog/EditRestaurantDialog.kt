package com.example.ordermenu.presentation.ui.staff.menu.components.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import com.example.ordermenu.presentation.ui.staff.menu.MenuViewModel

@Composable
fun EditRestaurantDialog(
    viewModel: MenuViewModel
) {
    AlertDialog(
        onDismissRequest = viewModel::toggleRestaurantDialog,
        title = {
            Text(text = "Restaurant Editor")
        },
        text = {
            RestaurantForm(viewModel = viewModel)
        },
        confirmButton = {
            TextButton(onClick = viewModel::updateRestaurant) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = viewModel::toggleRestaurantDialog) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun RestaurantForm(viewModel: MenuViewModel) {
    DialogTextField(
        value = viewModel.menuState.collectAsState().value.newRestaurantName,
        onValueChange = viewModel::updateRestaurantName,
        label = { Text("Name") }
    )
}