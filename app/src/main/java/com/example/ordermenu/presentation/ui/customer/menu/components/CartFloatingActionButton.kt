package com.example.ordermenu.presentation.ui.customer.menu.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.ordermenu.domain.model.order.Order

@Composable
fun CartFloatingActionButton(
    onClick: () -> Unit,
    count: Int
) {
    FloatingActionButton(onClick = onClick) {
        Box(
            modifier = Modifier.wrapContentSize()
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "View cart",
                modifier = Modifier.size(36.dp)
            )
            if(count > 0) {
                Badge(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .zIndex(1f),
                    containerColor = Color.Red,
                ) {
                    Text(
                        text = count.toString(),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White
                    )
                }
            }
        }
    }    
}