package com.example.ordermenu.presentation.ui.menu.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ordermenu.presentation.ui.menu.MenuViewModel

@Composable
fun CategoryNavigationDrawer(
    viewModel: MenuViewModel,
    innerPadding: PaddingValues
) {
    val menuState = viewModel.menuState.collectAsState().value
    PermanentNavigationDrawer(
        drawerContent = {
            PermanentDrawerSheet(
                modifier = Modifier.width(300.dp)
            ) {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    Spacer(modifier = Modifier.height(8.dp))
                    menuState.categories.map {
                        NavigationDrawerItem(
                            label = {Text(it.name)},
                            selected = menuState.category == it,
                            onClick = {viewModel.selectCategory(it)}
                        )
                    }
                    NavigationDrawerItem(
                        label = {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add category"
                            )
                        },
                        selected = false,
                        onClick = viewModel::toggleCategoryDialog
                    )
                }
            }
        },
        modifier = Modifier.padding(innerPadding)
    ) {
        DishGrid(itemList = menuState.dishes)
    }
}