package com.example.ordermenu.presentation.ui.staff.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ordermenu.presentation.ui.common.ItemTile
import com.example.ordermenu.presentation.ui.staff.StaffNavigationViewModel
import com.example.ordermenu.presentation.ui.staff.StaffScreens

@Composable
fun StaffModalDrawer(
    drawerState: DrawerState,
    navController: NavHostController,
    viewModel: StaffNavigationViewModel,
    closeDrawer: () -> Unit,
    onLogout: () -> Unit,
    content:  @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column (
                    modifier = Modifier.padding(16.dp)
                ){
                    Text("Order Menu", style = MaterialTheme.typography.titleMedium)
                    ItemTile(
                        icon = Icons.Default.Edit,
                        title = "Edit Menu"
                    ) {
                        navController.navigate(StaffScreens.Menu.name)
                        closeDrawer()
                    }
                    ItemTile(
                        icon = Icons.Default.Notifications,
                        title = "View Orders"
                    ) {
                        navController.navigate(StaffScreens.Tickets.name)
                        closeDrawer()
                    }
                    ItemTile(
                        icon = Icons.Default.Share,
                        title = "Share Restaurant QR"
                    ) {
                        viewModel.toggleQrDialog()
                    }
                    Spacer(
                        modifier = Modifier.weight(1f)
                    )
                    ItemTile(
                        icon = Icons.AutoMirrored.Filled.ExitToApp,
                        title = "Log Out"
                    ) {
                        onLogout()
                    }
                }
            }
        },
        content = content
    )
}