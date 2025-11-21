package com.example.myapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<FavoriteMeal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteMeal)

    @Query("DELETE FROM favorites WHERE idMeal = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT * FROM favorites WHERE idMeal = :id LIMIT 1")
    suspend fun getById(id: String): FavoriteMeal?

    @Query("SELECT * FROM favorites WHERE strMeal = :name LIMIT 1")
    suspend fun getByName(name: String): FavoriteMeal?
}