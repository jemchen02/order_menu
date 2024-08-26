package com.example.ordermenu.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.ordermenu.presentation.ui.staff.StaffPage
import com.example.ordermenu.presentation.ui.staff.menu.MenuPage
import com.example.ordermenu.presentation.ui.staff.tickets.TicketsPage
import com.example.ordermenu.presentation.ui.theme.OrderMenuTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OrderMenuTheme {
                StaffPage()
            }
        }
    }
}