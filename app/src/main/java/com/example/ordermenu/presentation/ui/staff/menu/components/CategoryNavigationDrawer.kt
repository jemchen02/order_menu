package com.example.ordermenu.presentation.ui.staff.menu.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ordermenu.presentation.ui.components.DishGrid
import com.example.ordermenu.presentation.ui.staff.menu.MenuViewModel

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
                        SwipeDismissable(
                            onDismiss = { viewModel.selectDeleteCategory(it) },
                            onEdit = { viewModel.selectEditCategory(it) }
                        ) {
                            NavigationDrawerItem(
                                label = {
                                    Text(
                                        text = it.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                selected = menuState.category == it,
                                onClick = {viewModel.selectCategory(it)}
                            )
                        }
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
        DishGrid(
            itemList = menuState.dishes,
            onDishTap = viewModel::selectDish
        )
    }
}