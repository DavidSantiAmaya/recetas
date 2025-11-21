package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Meal
import com.example.myapplication.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _randomMeal = MutableStateFlow<Meal?>(null)
    val randomMeal: StateFlow<Meal?> = _randomMeal.asStateFlow()

    val favorites = repository.getAllFavorites()

    private val _detailMeal = MutableStateFlow<Meal?>(null)
    val detailMeal: StateFlow<Meal?> = _detailMeal.asStateFlow()

    init {
        fetchRandomMeal()
    }

    fun fetchRandomMeal() {
        viewModelScope.launch {
            try {
                _randomMeal.value = repository.getRandomMeal()
            } catch (e: Exception) {
                _randomMeal.value = null
            }
        }
    }

    fun discardAndFetchNew() = fetchRandomMeal()

    fun saveCurrentAsFavorite() {
        val meal = _randomMeal.value ?: return
        viewModelScope.launch {
            repository.saveFavoriteMinimal(
                id = meal.idMeal ?: return@launch,
                name = meal.strMeal ?: "",
                thumb = meal.strMealThumb
            )
        }
    }

    fun loadDetailsById(id: String) {
        viewModelScope.launch {
            _detailMeal.value = repository.getMealById(id)
        }
    }

    fun loadDetailsByName(name: String) {
        viewModelScope.launch {
            _detailMeal.value = repository.getMealByName(name)
        }
    }
}