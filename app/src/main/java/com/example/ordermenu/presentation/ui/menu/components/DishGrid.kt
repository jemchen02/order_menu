package com.example.ordermenu.presentation.ui.menu.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ordermenu.domain.model.dish.Dish
import java.text.NumberFormat
import java.util.Locale

@Composable
fun DishGrid(
    itemList: List<Dish>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(300.dp)
    ) {
        items(
            items = itemList,
            key = {item -> item.id}
        ) {
            DishCard(it)
        }
    }
}


@Composable
fun DishCard(dish: Dish, modifier: Modifier = Modifier) {
    val formattedPrice = NumberFormat.getCurrencyInstance(Locale.US).format(dish.price)
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Column (
            modifier = Modifier.padding(8.dp)
        ){
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(dish.imageURL)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.3f)
                    .padding(4.dp)
            )
            Text(
                text = dish.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Quantity:",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = "${dish.calories} calories",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
            ){
                Text(
                    text = "+",
                    modifier = Modifier.clickable {}
                        .padding(horizontal = 8.dp),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = "0",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = "-",
                    modifier = Modifier.clickable {}
                        .padding(horizontal = 8.dp),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = formattedPrice,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}