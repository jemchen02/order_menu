package com.example.ordermenu.presentation.ui.staff

import android.app.Activity
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ordermenu.presentation.ui.staff.components.DisplayQrDialog
import com.example.ordermenu.presentation.ui.staff.components.StaffModalDrawer
import com.example.ordermenu.presentation.ui.staff.menu.MenuPage
import com.example.ordermenu.presentation.ui.staff.tickets.TicketsPage
import kotlinx.coroutines.launch

@Composable
fun StaffPage() {
    val viewModel = hiltViewModel<StaffNavigationViewModel>()
    val navState = viewModel.navState.collectAsState().value
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val onNavigationClick: () -> Unit = {
        scope.launch {
            drawerState.open()
        }
    }
    val activity = LocalContext.current as Activity
    StaffModalDrawer(
        drawerState = drawerState,
        navController = navController,
        closeDrawer = {
            scope.launch {
                drawerState.close()
            }
        },
        viewModel = viewModel,
        onLogout = {
            viewModel.logout()
            activity.finish()
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = StaffScreens.Menu.name
        ) {
            composable(StaffScreens.Tickets.name) {
                TicketsPage(
                    onNavigationClick = onNavigationClick
                )
            }
            composable(StaffScreens.Menu.name) {
                MenuPage(
                    viewTickets = {
                        navController.navigate(StaffScreens.Tickets.name)
                    },
                    onNavigationClick = onNavigationClick
                )
            }
        }
        if(navState.showQrDialog) {
            DisplayQrDialog(viewModel = viewModel)
        }
    }

}