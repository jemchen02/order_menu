package com.example.ordermenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ordermenu.data.fake.MenuItemFakes
import com.example.ordermenu.presentation.ui.menu.MenuPage
import com.example.ordermenu.presentation.ui.menu.components.MenuItemGrid
import com.example.ordermenu.presentation.ui.theme.OrderMenuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OrderMenuTheme {
                MenuPage()
            }
        }
    }
}