package com.example.myapplication.network

import com.example.myapplication.model.MealsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMealDbService {
    @GET("random.php")
    suspend fun getRandomMeal(): MealsResponse

    @GET("search.php")
    suspend fun searchByName(@Query("s") name: String): MealsResponse

    @GET("lookup.php")
    suspend fun lookupById(@Query("i") id: String): MealsResponse

    companion object {
        const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
    }
}

