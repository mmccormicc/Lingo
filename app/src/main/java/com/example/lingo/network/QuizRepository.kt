package com.example.lingo.network

import com.example.lingo.data.QuizScore
import com.example.lingo.network.QuizApiService
import retrofit2.Response

class QuizRepository(private val apiService: QuizApiService) {

    suspend fun submitScore(score: QuizScore): Response<QuizScore> {
        return apiService.submitScore(score)
    }

    suspend fun getScore(deviceId: String, language: String, quizName: String): Response<QuizScore> {
        return apiService.getScore(deviceId, language, quizName)
    }
}