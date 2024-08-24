package com.example.ordermenu.presentation.ui.menu.staff.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ordermenu.domain.model.order.OrderTicket
import com.example.ordermenu.domain.util.getMinutesAgo
import com.example.ordermenu.domain.util.getPriceString
import com.example.ordermenu.presentation.ui.components.LineDivider
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TicketList(
    contentPaddingValues: PaddingValues = PaddingValues(0.dp),
    tickets: List<OrderTicket>
) {
    LazyVerticalGrid (
        columns = GridCells.Adaptive(400.dp),
        modifier = Modifier.padding(contentPaddingValues)
    ){
        items(items = tickets, key = {item -> item.id}) {
            TicketItem(
                ticket = it
            )
        }
    }
}

@Composable
fun TicketItem(
    ticket: OrderTicket
) {
    Card (
        modifier = Modifier.padding(8.dp)
    ){
        Column (
            modifier = Modifier.padding(8.dp)
        ){
            Text(
                text = getMinutesAgo(ticket.time),
                style = MaterialTheme.typography.bodyLarge
            )
            LineDivider()
            ticket.items.entries.map {
                TicketDetail(
                    item = it
                )
            }
            LineDivider()
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

