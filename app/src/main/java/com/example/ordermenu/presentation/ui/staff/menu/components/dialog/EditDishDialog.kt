package com.example.ordermenu.presentation.ui.staff.menu.components.dialog

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ordermenu.presentation.ui.staff.menu.DishFields
import com.example.ordermenu.presentation.ui.staff.menu.MenuViewModel

@Composable
fun EditDishDialog(
    viewModel: MenuViewModel
) {
    val currDish = viewModel.menuState.collectAsState().value.dish
    AlertDialog(
        onDismissRequest = viewModel::toggleDishDialog,
        title = {
            if(currDish.id.isNotEmpty()) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(text = "Edit Dish")
                    IconButton(onClick = viewModel::toggleDeleteDishDialog) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete dish"
                        )
                    }
                }
            } else {
                Text("Add Dish")
            }
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
            TextButton(onClick = viewModel::toggleDishDialog) {
                Text("Cancel")
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
    Row {
        Column (
            modifier = Modifier.weight(1f),
        ){
            Button(onClick = {
                singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }) {
                Text(text = "Pick a photo")
            }
            if(currDish.imageURL.isNotEmpty()) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(currDish.imageURL)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            DialogTextField(
                value = currDish.name,
                onValueChange = {
                    viewModel.updateField(DishFields.DISH_NAME, it)
                },
                label = { Text("Name") }
            )
            DialogTextField(
                value = currDish.allergens,
                onValueChange = {
                    viewModel.updateField(DishFields.ALLERGENS, it)
                },
                label = { Text("Allergens") }
            )
            DialogTextField(
                value = currDish.price,
                onValueChange = {
                    viewModel.updateField(DishFields.PRICE, it)
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal
                ),
                label = { Text("Price") }
            )
            DialogTextField(
                value = currDish.calories,
                onValueChange = {
                    viewModel.updateField(DishFields.CALORIES, it)
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                label = { Text("Calories") }
            )
            Text(
                text = viewModel.menuState.collectAsState().value.dishError ?: "",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
