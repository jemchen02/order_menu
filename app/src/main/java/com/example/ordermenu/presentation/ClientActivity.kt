package com.example.ordermenu.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ordermenu.presentation.ui.customer.menu.CustomerMenuPage
import com.example.ordermenu.presentation.ui.theme.OrderMenuTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClientActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrderMenuTheme {
                CustomerMenuPage()
            }
        }
    }
}