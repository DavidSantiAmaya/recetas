package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.viewmodel.MainViewModel
import com.example.myapplication.utils.parseIngredients
import coil.compose.AsyncImage

@Composable
fun DetailScreen(viewModel: MainViewModel, id: String?) {
    LaunchedEffect(id) {
        id?.let { viewModel.loadDetailsById(it) }
    }

    val meal = viewModel.detailMeal.collectAsState().value

    if (meal == null) {
        Text("Cargando detalles...")
        return
    }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        item {
            AsyncImage(model = meal.strMealThumb, contentDescription = meal.strMeal, modifier = Modifier.fillMaxWidth().height(220.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(meal.strMeal ?: "")
            Text(text = "${meal.strCategory ?: ""} • ${meal.strArea ?: ""}")
            Spacer(modifier = Modifier.height(6.dp))
            Text("Instrucciones:")
            Text(meal.strInstructions ?: "")
            Spacer(modifier = Modifier.height(6.dp))
            Text("Ingredientes:")
        }
        val ingredients = parseIngredients(meal)
        items(ingredients.size) { idx ->
            Text(ingredients[idx])
        }
    }
}
