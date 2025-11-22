package com.example.myapplication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import com.example.myapplication.db.AppDatabase
import com.example.myapplication.repository.RecipeRepository
import com.example.myapplication.ui.screens.RandomRecipeScreen
import com.example.myapplication.ui.screens.FavoritesScreen
import com.example.myapplication.ui.screens.DetailScreen
import com.example.myapplication.viewmodel.MainViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(applicationContext)
        val repository = RecipeRepository(db.favoritesDao())

        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(repository) as T
            }
        }

        setContent {
            val navController = rememberNavController()
            val vm: MainViewModel = viewModel(factory = factory)

            Surface() {
                NavHost(navController = navController, startDestination = "random") {
                    composable("random") {
                        RandomRecipeScreen(viewModel = vm, onGoToFavorites = { navController.navigate("favorites") }, onOpenDetail = { id -> navController.navigate("detail/$id") })
                    }
                    composable("favorites") {
                        FavoritesScreen(
                            viewModel = vm,
                            onOpenDetail = { id -> navController.navigate("detail/$id") },
                            GoBack = { navController.popBackStack() }
                        )
                    }
                    composable("detail/{id}", arguments = listOf(navArgument("id") { type = NavType.StringType })) { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("id")
                        DetailScreen(viewModel = vm, id = id, GoBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}


//- models/Meal.kt
//- network/TheMealDbService.kt
//- db/FavoriteMeal.kt
//- db/FavoritesDao.kt
//- db/AppDatabase.kt
//- repository/RecipeRepository.kt
//- viewmodel/MainViewModel.kt
//- ui/MainActivity.kt
//- ui/screens/RandomRecipeScreen.kt
//- ui/screens/FavoritesScreen.kt
//- ui/screens/DetailScreen.kt
//- utils/IngredientParser.kt