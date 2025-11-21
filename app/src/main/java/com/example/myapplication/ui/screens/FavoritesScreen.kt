package com.example.myapplication.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.viewmodel.MainViewModel

@Composable
fun FavoritesScreen(viewModel: MainViewModel, onOpenDetail: (String) -> Unit) {
    val favs = viewModel.favorites.collectAsState(initial = emptyList()).value

    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        Text("Favoritos", modifier = Modifier.padding(8.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(favs) { fav ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenDetail(fav.idMeal) }
                    .padding(8.dp)) {
                    AsyncImage(model = fav.strMealThumb, contentDescription = fav.strMeal, modifier = Modifier.size(80.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(fav.strMeal)
                        Text(text = "ID: ${fav.idMeal}")
                    }
                }
            }
        }
    }
}
