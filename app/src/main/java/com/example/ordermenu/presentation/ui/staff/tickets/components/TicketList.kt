package com.example.ordermenu.presentation.ui.staff.tickets.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ordermenu.domain.model.order.OrderTicket
import com.example.ordermenu.domain.util.getMinutesAgo
import com.example.ordermenu.domain.util.getPriceString
import com.example.ordermenu.presentation.ui.common.LineDivider
import kotlinx.coroutines.delay

@Composable
fun TicketList(
    onTicketClick: (OrderTicket) -> Unit,
    contentPaddingValues: PaddingValues = PaddingValues(0.dp),
    tickets: List<OrderTicket>
) {
    LazyVerticalGrid (
        columns = GridCells.Adaptive(400.dp),
        modifier = Modifier.padding(contentPaddingValues)
    ){
        items(items = tickets, key = {item -> item.id}) {
            TicketItem(
                onTicketClick = onTicketClick,
                ticket = it
            )
        }
    }
}

@Composable
fun TicketItem(
    onTicketClick: (OrderTicket) -> Unit,
    ticket: OrderTicket
) {
    var minutesAgo by remember{ mutableStateOf("") }
    LaunchedEffect(Unit) {
        while (true) {
            minutesAgo = getMinutesAgo(ticket.time)
            delay(60000)
        }
    }
    Card (
        modifier = Modifier
            .padding(8.dp)
            .clickable { onTicketClick(ticket) }
    ){
        Column (
            modifier = Modifier.padding(8.dp)
        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                var tableText = ""
                if(ticket.table.isNotEmpty()) {
                    tableText = "Table ${ticket.table}"
                }
                Text(
                    text = tableText,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = minutesAgo,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            LineDivider()
            ticket.items.entries.map {
                TicketDetail(
                    item = it
                )
            }
            LineDivider()
            if(ticket.additionalInstructions.isNotEmpty()) {
                Text(
                    text = ticket.additionalInstructions,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total:",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = getPriceString(ticket.totalPrice)
                )
            }

        }
    }
}

@Composable
fun TicketDetail(
    item: Map.Entry<String, Int>
) {
    Row (
        modifier = Modifier
            .padding(bottom = 12.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = item.key,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = item.value.toString(),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

