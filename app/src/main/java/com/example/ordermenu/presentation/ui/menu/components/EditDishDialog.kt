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
import androidx.compose.ui.graphics.Color
import com.example.ordermenu.domain.model.dish.DishFields
import com.example.ordermenu.presentation.ui.menu.MenuViewModel

@Composable
fun EditDishDialog(
    viewModel: MenuViewModel
) {
    AlertDialog(
        onDismissRequest = viewModel::toggleDialog,
        title = {
            Text(text = "Add Item")
        },
        text = {
            DishForm(viewModel = viewModel)
        },
        confirmButton = {
            TextButton(onClick = viewModel::addDish) {
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
fun DishForm(viewModel: MenuViewModel) {
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = viewModel::uploadImage
    )
    val currDish = viewModel.menuState.collectAsState().value.dish
    Column {
        TextField(
            value = currDish.name,
            onValueChange = {
                viewModel.updateField(DishFields.NAME, it)
            },
            label = { Text("Name") }
        )
        TextField(
            value = currDish.allergens,
            onValueChange = {
                viewModel.updateField(DishFields.ALLERGENS, it)
            },
            label = { Text("Allergens") }
        )
        TextField(
            value = currDish.price,
            onValueChange = {
                viewModel.updateField(DishFields.PRICE, it)
            },
            label = { Text("Price") }
        )
        TextField(
            value = currDish.calories,
            onValueChange = {
                viewModel.updateField(DishFields.CALORIES, it)
            },
            label = { Text("Calories") }
        )
        Button(onClick = {
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }) {
            Text(text = "Pick a photo")
        }
        Text(
            text = viewModel.menuState.collectAsState().value.error?: "",
            color = Color.Red
        )
    }
}