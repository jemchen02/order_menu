package com.example.ordermenu.presentation.ui.menu.customer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ordermenu.presentation.ui.components.OrderMenuAppBar
import com.example.ordermenu.presentation.ui.menu.customer.components.CartFloatingActionButton
import com.example.ordermenu.presentation.ui.menu.customer.components.CustomerCategoryDrawer
import com.example.ordermenu.presentation.ui.menu.customer.components.OrderDialog

@Composable
fun CustomerMenuPage() {
    val viewModel = hiltViewModel<CustomerMenuViewModel>()
    val menuState = viewModel.menuState.collectAsState().value
    Scaffold(
        topBar = {
            OrderMenuAppBar()
        },
        floatingActionButton = {
            CartFloatingActionButton(
                onClick = viewModel::toggleOrder,
                count = menuState.order.totalitems
            )
        }
    ) { innerPadding ->
        CustomerCategoryDrawer(
            viewModel = viewModel,
            innerPadding = innerPadding
        )
        if(menuState.showOrder) {
            OrderDialog(
                viewModel = viewModel
            )
        }
    }
}