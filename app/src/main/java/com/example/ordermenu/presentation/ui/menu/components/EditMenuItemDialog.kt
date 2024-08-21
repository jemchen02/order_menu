package com.example.ordermenu.presentation.ui.menu.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.ordermenu.data.local.table.MenuField
import com.example.ordermenu.data.local.table.MenuItem
import com.example.ordermenu.presentation.ui.menu.menu.MenuViewModel

@Composable
fun EditMenuItemDialog(
    viewModel: MenuViewModel
) {
    AlertDialog(
        onDismissRequest = viewModel::toggleDialog,
        title = {
            Text(text = "Add Item")
        },
        text = {
            MenuItemForm(viewModel = viewModel)
        },
        confirmButton = {
            TextButton(onClick = viewModel::addMenuItem) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = viewModel::toggleDialog) {
                Text("Dismiss")
            }
        }
    )
}

@Composable
fun MenuItemForm(viewModel: MenuViewModel) {
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = viewModel::uploadImage
    )
    val currMenuItem = viewModel.menuState.collectAsState().value.menuItem
    Column {
        TextField(
            value = currMenuItem[MenuField.NAME] ?: "",
            onValueChange = {
                viewModel.updateField(MenuField.NAME, it)
            },
            label = {Text("Name")}
        )
        TextField(
            value = currMenuItem[MenuField.ALLERGENS] ?: "",
            onValueChange = {
                viewModel.updateField(MenuField.ALLERGENS, it)
            },
            label = {Text("Allergens")}
        )
        TextField(
            value = currMenuItem[MenuField.PRICE] ?: "",
            onValueChange = {
                viewModel.updateField(MenuField.PRICE, it)
            },
            label = {Text("Price")}
        )
        TextField(
            value = currMenuItem[MenuField.CALORIES] ?: "",
            onValueChange = {
                viewModel.updateField(MenuField.CALORIES, it)
            },
            label = {Text("Calories")}
        )
        Button(onClick = {
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }) {
            Text(text = "Pick a photo")
        }
    }
}