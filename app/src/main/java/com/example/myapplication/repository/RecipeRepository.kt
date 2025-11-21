package com.example.myapplication.repository

import com.example.myapplication.db.FavoriteMeal
import com.example.myapplication.db.FavoritesDao
import com.example.myapplication.model.Meal
import com.example.myapplication.network.RetrofitClient

class RecipeRepository(private val favoritesDao: FavoritesDao) {

    private val api = RetrofitClient.api

    suspend fun getRandomMeal(): Meal? {
        val resp = api.getRandomMeal()
        return resp.meals?.firstOrNull()
    }

    suspend fun getMealById(id: String): Meal? {
        val resp = api.lookupById(id)
        return resp.meals?.firstOrNull()
    }

    suspend fun getMealByName(name: String): Meal? {
        val resp = api.searchByName(name)
        return resp.meals?.firstOrNull()
    }

    // Favorites
    fun getAllFavorites() = favoritesDao.getAllFavorites()

    suspend fun saveFavoriteMinimal(id: String, name: String, thumb: String?) {
        favoritesDao.insert(FavoriteMeal(idMeal = id, strMeal = name, strMealThumb = thumb))
    }

    suspend fun deleteFavorite(id: String) {
        favoritesDao.deleteById(id)
    }

    suspend fun getFavoriteById(id: String) = favoritesDao.getById(id)
    suspend fun getFavoriteByName(name: String) = favoritesDao.getByName(name)
}
