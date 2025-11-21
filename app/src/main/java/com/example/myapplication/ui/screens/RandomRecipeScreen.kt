package com.example.myapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.viewmodel.MainViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun RandomRecipeScreen(viewModel: MainViewModel, onGoToFavorites: () -> Unit, onOpenDetail: (String) -> Unit) {
    val meal = viewModel.randomMeal.collectAsState().value

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = onGoToFavorites) { Text(text = "Favoritos") }
            Button(onClick = { viewModel.fetchRandomMeal() }) { Text("Actualizar") }
        }

        if (meal != null) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    AsyncImage(model = meal.strMealThumb, contentDescription = meal.strMeal, modifier = Modifier.fillMaxWidth().height(200.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = meal.strMeal ?: "Sin nombre")
                    Text(text = "${meal.strCategory ?: ""} • ${meal.strArea ?: ""}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = meal.strInstructions?.take(250) ?: "Sin instrucciones")
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { viewModel.discardAndFetchNew() }, modifier = Modifier.weight(1f)) { Text("Descartar") }
                Button(onClick = {
                    viewModel.saveCurrentAsFavorite()
                }, modifier = Modifier.weight(1f)) { Text("Guardar en Favoritos") }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { meal.idMeal?.let { onOpenDetail(it) } }) { Text("Ver detalles completos") }
        } else {
            Text("Cargando receta aleatoria...")
        }
    }
}