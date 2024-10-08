package com.example.ordermenu.presentation.ui.customer.menu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ordermenu.domain.util.getPriceString
import com.example.ordermenu.presentation.ui.common.LineDivider
import com.example.ordermenu.presentation.ui.customer.menu.CustomerMenuViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun OrderDialog(
    viewModel: CustomerMenuViewModel,
) {
    AlertDialog(
        onDismissRequest = viewModel::toggleOrder,
        title = {
            Text(text = "Order")
        },
        text = {
            OrderForm(viewModel = viewModel)
        },
        confirmButton = {
            TextButton(onClick = viewModel::submitOrder) {
                Text(
                    "Place Order",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        },
        dismissButton = {
            TextButton(onClick = viewModel::toggleOrder) {
                Text(
                    "Cancel",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    )
}
@Composable
fun OrderForm(viewModel: CustomerMenuViewModel) {
    val order = viewModel.menuState.collectAsState().value.order
    LazyColumn {
        items(order.items.entries.toList(), key = {(dish, quantity) -> dish.id}) {
            (dish, quantity) ->
            val formattedPrice = NumberFormat.getCurrencyInstance(Locale.US).format(dish.price * quantity)
            Column (
                modifier = Modifier.padding(bottom = 12.dp)
            ){
                Text(
                    text = dish.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "-",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .clickable { viewModel.removeDish(dish) }
                            .background(MaterialTheme.colorScheme.primary, CircleShape)
                            .padding(horizontal = 8.dp)
                    )
                    Text(
                        text = quantity.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Text(
                        text = "+",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .clickable { viewModel.addDish(dish) }
                            .background(MaterialTheme.colorScheme.primary, CircleShape)
                            .padding(horizontal = 8.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = formattedPrice,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
        item {
            Column {
                if(order.items.isEmpty()) {
                    Text(
                        text = "Your cart is empty",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                TextField(
                    value = order.table,
                    onValueChange = viewModel::updateTable,
                    placeholder = {
                        Text("Table Number", style = MaterialTheme.typography.labelLarge)
                    },
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 12.dp),
                    textStyle = MaterialTheme.typography.labelLarge
                )
                TextField(
                    value = order.additionalInstructions,
                    onValueChange = viewModel::updateInstructions,
                    placeholder = {
                        Text("Add additional instructions", style = MaterialTheme.typography.labelLarge)
                    },
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 12.dp),
                    textStyle = MaterialTheme.typography.labelLarge
                )
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = "Total:",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = getPriceString(order.totalPrice),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}