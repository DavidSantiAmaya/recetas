package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Meal
import com.example.myapplication.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class NavigationEvent {
    object GoBack : NavigationEvent()
    object GoToFavorites : NavigationEvent()
    data class GoToDetail(val id: String) : NavigationEvent()
}

class MainViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _randomMeal = MutableStateFlow<Meal?>(null)
    val randomMeal: StateFlow<Meal?> = _randomMeal.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    val favorites = repository.getAllFavorites()

    private val _detailMeal = MutableStateFlow<Meal?>(null)
    val detailMeal: StateFlow<Meal?> = _detailMeal.asStateFlow()

    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent: StateFlow<NavigationEvent?> = _navigationEvent.asStateFlow()

    init {
        fetchRandomMeal()
    }

    fun fetchRandomMeal() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                _randomMeal.value = repository.getRandomMeal()
            } catch (e: Exception) {
                _randomMeal.value = null
                _errorMessage.value = e.message ?: "Error desconocido al obtener receta"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun discardAndFetchNew() = fetchRandomMeal()

    /**
     * Guarda solo los datos esenciales en la BD local (Room):
     * idMeal, strMeal, strMealThumb
     */
    fun saveCurrentAsFavorite() {
        val meal = _randomMeal.value ?: return
        viewModelScope.launch {
            _errorMessage.value = null
            try {
                repository.saveFavoriteMinimal(
                    id = meal.idMeal ?: return@launch,
                    name = meal.strMeal ?: "",
                    thumb = meal.strMealThumb
                )
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "No se pudo guardar en favoritos"
            }
        }
    }

    /**
     * Elimina favorito por id (útil para la pantalla de favoritos si quieres un botón borrar).
     */
    fun removeFavoriteById(id: String) {
        viewModelScope.launch {
            _errorMessage.value = null
            try {
                repository.deleteFavorite(id)
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "No se pudo borrar el favorito"
            }
        }
    }

    /**
     * Cargar detalles completos por ID (lookup.php?i=...)
     */
    fun loadDetailsById(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                _detailMeal.value = repository.getMealById(id)
            } catch (e: Exception) {
                _detailMeal.value = null
                _errorMessage.value = e.message ?: "Error al cargar detalles por ID"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadDetailsByName(name: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                _detailMeal.value = repository.getMealByName(name)
            } catch (e: Exception) {
                _detailMeal.value = null
                _errorMessage.value = e.message ?: "Error al cargar detalles por nombre"
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun goBack() {
        _navigationEvent.value = NavigationEvent.GoBack
    }

    fun goToFavorites() {
        _navigationEvent.value = NavigationEvent.GoToFavorites
    }

    fun goToDetail(id: String) {
        _navigationEvent.value = NavigationEvent.GoToDetail(id)
    }

    fun clearNavigationEvent() {
        _navigationEvent.value = null
    }
}
