package com.example.myapplication.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteMeal(
    @PrimaryKey val idMeal: String,
    val strMeal: String,
    val strMealThumb: String?
)
