package com.example.ordermenu.presentation.ui.staff.menu.components.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import com.example.ordermenu.presentation.ui.staff.menu.MenuViewModel

@Composable
fun EditCategoryDialog(
    viewModel: MenuViewModel
) {
    AlertDialog(
        onDismissRequest = viewModel::toggleCategoryDialog,
        title = {
            Text(text = "Category Editor")
        },
        text = {
            CategoryForm(viewModel = viewModel)
        },
        confirmButton = {
            TextButton(onClick = viewModel::addCategory) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = viewModel::toggleCategoryDialog) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun CategoryForm(viewModel: MenuViewModel) {
    DialogTextField(
        value = viewModel.menuState.collectAsState().value.newCategory,
        onValueChange = viewModel::updateNewCategory,
        label = { Text("Name") }
    )
    Text(
        text = viewModel.menuState.collectAsState().value.categoryError ?: "",
        color = Color.Red
    )
}