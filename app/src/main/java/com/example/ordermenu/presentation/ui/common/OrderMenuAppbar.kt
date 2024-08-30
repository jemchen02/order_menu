package com.example.ordermenu.presentation.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderMenuAppBar(
    title: String = "Order Menu",
    onNameClick: () -> Unit = {},
    onNavigationClick: () -> Unit = {},
    actions: @Composable() (RowScope.() -> Unit) = {},
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.clickable { onNameClick() }
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        actions = actions,
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "open navigation menu",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    )
}