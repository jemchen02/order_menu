package com.example.ordermenu.presentation.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun DrawerItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row (
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            imageVector = icon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(text = title)
    }
}