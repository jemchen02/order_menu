package com.example.ordermenu.presentation.ui.customer.menu

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ordermenu.presentation.ui.components.OrderMenuAppBar
import com.example.ordermenu.presentation.ui.customer.menu.components.CartFloatingActionButton
import com.example.ordermenu.presentation.ui.customer.menu.components.CustomerCategoryDrawer
import com.example.ordermenu.presentation.ui.customer.menu.components.OrderDialog

@Composable
fun CustomerMenuPage() {
    val viewModel = hiltViewModel<CustomerMenuViewModel>()
    val menuState = viewModel.menuState.collectAsState().value
    Scaffold(
        topBar = {
            OrderMenuAppBar(
                title = menuState.restaurant?.name ?: "Order Menu"
            )
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