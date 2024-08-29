package com.example.ordermenu.presentation.ui.customer.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ordermenu.presentation.ui.components.DrawerItem
import com.example.ordermenu.presentation.ui.customer.menu.CustomerMenuViewModel

@Composable
fun CustomerModalDrawer(
    drawerState: DrawerState,
    viewModel: CustomerMenuViewModel,
    onExit: () -> Unit,
    content:  @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column (
                    modifier = Modifier.padding(8.dp)
                ){
                    Text("Order Menu (Customer)")
                    DrawerItem(
                        icon = Icons.Default.ShoppingCart,
                        title = "View Order"
                    ) {
                        viewModel.toggleOrder()
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    DrawerItem(
                        icon = Icons.AutoMirrored.Filled.ExitToApp,
                        title = "Close Restaurant"
                    ) {
                        onExit()
                    }
                }
            }
        },
        content = content
    )
}