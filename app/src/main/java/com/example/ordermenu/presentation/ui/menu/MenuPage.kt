package com.example.ordermenu.presentation.ui.menu

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ordermenu.data.fake.MenuItemFakes
import com.example.ordermenu.presentation.ui.components.OrderMenuAppBar
import com.example.ordermenu.presentation.ui.menu.components.MenuItemGrid

@Composable
fun MenuPage() {
    Scaffold (
        topBar = {
            OrderMenuAppBar()
        }
    ){ innerPadding->
        MenuItemGrid(
            itemList = MenuItemFakes.menuItems,
            modifier = Modifier.padding(innerPadding)
        )
    }
}