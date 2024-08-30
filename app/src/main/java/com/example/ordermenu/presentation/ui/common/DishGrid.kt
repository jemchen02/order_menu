package com.example.ordermenu.presentation.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ordermenu.domain.model.dish.Dish
import com.example.ordermenu.domain.util.getPriceString

@Composable
fun DishGrid(
    itemList: List<Dish>,
    onDishTap: (Dish) -> Unit,
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
            DishCard(
                dish = it,
                onDishTap = onDishTap
            )
        }
    }
}


@Composable
fun DishCard(
    dish: Dish,
    onDishTap: (Dish) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
            .clickable {
                onDishTap(dish)
            }
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
                    text = "${dish.calories} calories",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = getPriceString(dish.price),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            Text(
                text = "Contains ${dish.allergens}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
        }
    }
}