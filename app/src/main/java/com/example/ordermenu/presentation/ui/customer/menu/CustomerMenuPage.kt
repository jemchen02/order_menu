package com.example.ordermenu.presentation.ui.customer.menu

import android.app.Activity
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ordermenu.presentation.ui.components.OrderMenuAppBar
import com.example.ordermenu.presentation.ui.customer.components.CustomerModalDrawer
import com.example.ordermenu.presentation.ui.customer.menu.components.CartFloatingActionButton
import com.example.ordermenu.presentation.ui.customer.menu.components.CustomerCategoryDrawer
import com.example.ordermenu.presentation.ui.customer.menu.components.OrderDialog
import kotlinx.coroutines.launch

@Composable
fun CustomerMenuPage() {
    val viewModel = hiltViewModel<CustomerMenuViewModel>()
    val menuState = viewModel.menuState.collectAsState().value
    val activity = LocalContext.current as Activity
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val onNavigationClick: () -> Unit = {
        scope.launch {
            drawerState.open()
        }
    }
    CustomerModalDrawer(
        drawerState = drawerState,
        viewModel = viewModel,
        onExit = {
            viewModel.exitRestaurant()
            activity.finish()
        }
    ) {
        Scaffold(
            topBar = {
                OrderMenuAppBar(
                    title = menuState.restaurant?.name ?: "Order Menu",
                    onNavigationClick = onNavigationClick
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
}