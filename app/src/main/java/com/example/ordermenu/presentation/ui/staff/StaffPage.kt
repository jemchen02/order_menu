package com.example.ordermenu.presentation.ui.staff

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ordermenu.presentation.ui.staff.menu.MenuPage
import com.example.ordermenu.presentation.ui.staff.tickets.TicketsPage

@Composable
fun StaffPage() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = StaffScreens.Tickets.name
    ) {
        composable(StaffScreens.Tickets.name) {
            TicketsPage(
                editMenu = {
                    navController.navigate(StaffScreens.Menu.name)
                }
            )
        }
        composable(StaffScreens.Menu.name) {
            MenuPage(
                viewTickets = {
                    navController.navigate(StaffScreens.Tickets.name)
                }
            )
        }
    }
}