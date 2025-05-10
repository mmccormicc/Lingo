package com.example.lingo.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    // Url to back-end server hosted on railway
    // Transmission done with https
    private const val BASE_URL = "https://lingobackend-production.up.railway.app/"

    // Creating QuizApiService with connection to back-end server
    val quizApiService: QuizApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuizApiService::class.java)
    }
}