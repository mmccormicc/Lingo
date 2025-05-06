package com.example.lingo.network

import com.example.lingo.data.QuizScore
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface QuizApiService {
    @POST("scores")
    suspend fun submitScore(@Body score: QuizScore): Response<QuizScore>

    @GET("score")
    suspend fun getScore(
        @Query("deviceId") deviceId: String,
        @Query("language") language: String,
        @Query("quizName") quizName: String
    ): Response<QuizScore>
}