package com.example.ordermenu.presentation.ui.menu.menu

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ordermenu.data.fake.MenuItemFakes
import com.example.ordermenu.presentation.ui.components.OrderMenuAppBar
import com.example.ordermenu.presentation.ui.menu.components.MenuItemGrid

@Composable
fun MenuPage() {
    val viewModel = hiltViewModel<MenuViewModel>()
    val menuItems = viewModel.getAllMenuItems().collectAsState(emptyList()).value
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = viewModel::uploadImage
    )
    Scaffold (
        topBar = {
            OrderMenuAppBar()
        }
    ){ innerPadding->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            MenuItemGrid(
                itemList = menuItems
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
}