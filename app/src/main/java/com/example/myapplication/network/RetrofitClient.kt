package com.example.myapplication.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val api: TheMealDbService by lazy {
        Retrofit.Builder()
            .baseUrl(TheMealDbService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheMealDbService::class.java)
    }
}
