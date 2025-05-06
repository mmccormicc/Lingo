package com.example.lingo.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    private const val BASE_URL = "https://your-api-url.com/"

    val quizApiService: QuizApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuizApiService::class.java)
    }
}