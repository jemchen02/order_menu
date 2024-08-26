package com.example.ordermenu.presentation.ui.staff.menu.components.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.ordermenu.presentation.ui.staff.menu.MenuViewModel

@Composable
fun DeleteCategoryDialog(
    viewModel: MenuViewModel
) {
    val name = viewModel.menuState.collectAsState().value.editingCategory?.name ?: "Unknown category"
    AlertDialog(
        onDismissRequest = viewModel::toggleDeleteCategoryDialog,
        title = {
            Text(text = name)
        },
        text = {
            Text(text = "Are you sure you want to delete this category?")
        },
        confirmButton = {
            TextButton(onClick = viewModel::deleteCategory) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = viewModel::toggleDeleteCategoryDialog) {
                Text("Cancel")
            }
        }
    )
}