package com.example.ordermenu.presentation.ui.staff.menu.components.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.ordermenu.presentation.ui.staff.menu.MenuViewModel

@Composable
fun DeleteDishDialog(
    viewModel: MenuViewModel
) {
    val name = viewModel.menuState.collectAsState().value.dish.name
    AlertDialog(
        onDismissRequest = viewModel::toggleDeleteDishDialog,
        title = {
            Text(text = name)
        },
        text = {
            Text(text = "Are you sure you want to delete this dish?")
        },
        confirmButton = {
            TextButton(onClick = viewModel::deleteDish) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = viewModel::toggleDeleteDishDialog) {
                Text("Cancel")
            }
        }
    )
}