package com.example.ordermenu.presentation.ui.staff.menu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ordermenu.domain.model.category.DishCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeDismissable(
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    content: @Composable() (RowScope.() -> Unit)
) {
    val dismissState = rememberSwipeToDismissBoxState()
    LaunchedEffect(dismissState.currentValue) {
        when (dismissState.currentValue) {
            SwipeToDismissBoxValue.EndToStart -> {
                onDismiss()
                dismissState.snapTo(SwipeToDismissBoxValue.Settled)
            }
            SwipeToDismissBoxValue.StartToEnd -> {
                onEdit()
                dismissState.snapTo(SwipeToDismissBoxValue.Settled)
            }
            else -> Unit
        }
    }
    val color = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.tertiary
        SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.secondary
        SwipeToDismissBoxValue.Settled -> Color.Transparent
    }
    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = { DismissBackground(dismissState) },
        content = content,
        modifier = Modifier.background(color)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: SwipeToDismissBoxState) {

    Row (
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalArrangement = when(dismissState.dismissDirection) {
            SwipeToDismissBoxValue.EndToStart -> Arrangement.End
            else -> Arrangement.Start
        },
        verticalAlignment = Alignment.CenterVertically
    ){
        when(dismissState.dismissDirection) {
            SwipeToDismissBoxValue.StartToEnd -> {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "edit",
                    tint = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            SwipeToDismissBoxValue.EndToStart -> {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "delete",
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }

            SwipeToDismissBoxValue.Settled -> Unit
        }
    }
}