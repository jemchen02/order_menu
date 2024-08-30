package com.example.ordermenu.presentation.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LineDivider() {
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        thickness = 2.dp,
        color = Color.Gray
    )
}