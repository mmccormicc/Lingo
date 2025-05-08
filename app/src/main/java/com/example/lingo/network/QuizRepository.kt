package com.example.lingo.network

import android.content.Context
import android.util.Log
import com.example.lingo.data.QuizScore
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

class QuizRepository(private val apiService: QuizApiService) {

    suspend fun submitDeviceId(deviceId: String) {
        try {
            // Calling submitDeviceId from api service
            val response = apiService.submitDeviceId(deviceId)
            // Logging response
            if (response.isSuccessful) {
                Log.d("QuizRepository", "Device ID submitted successfully")
            } else {
                Log.e("QuizRepository", "Failed to submit device ID: ${response.code()} ${response.message()}")
            }
        // Catching HTTP specific exceptions
        } catch (e: HttpException) {
            when (e.code()) {
                400 -> Log.e("QuizRepository", "Bad Request: ${e.message()}")
                401 -> Log.e("QuizRepository", "Unauthorized")
                404 -> Log.e("QuizRepository", "Not Found")
                500 -> Log.e("QuizRepository", "Server Error")
                else -> Log.e("QuizRepository", "HTTP error ${e.code()}: ${e.message()}")
            }
        // Catching network exceptions
        } catch (e: IOException) {
            Log.e("QuizRepository", "Network error", e)
        // Catching generic exceptions
        } catch (e: Exception) {
            Log.e("QuizRepository", "Unexpected error", e)
        }
    }

    suspend fun submitScore(score: QuizScore, context: Context): Result<Unit> {
        return try {
            // Calling submitscore from api service
            val response = apiService.submitScore(score)
            // Logging response
            if (response.isSuccessful) {
                Log.d("QuizRepository", "Score submitted successfully")
                Result.success(Unit)
            } else {
                Log.e("QuizRepository", "Server error: ${response.code()} ${response.message()}")
                // Caching score that failed to sent in sharedprefs object
                cacheUnsentScore(context, score)
                Result.failure(Exception("Server error: ${response.message()}"))
            }
        // Catching HTTP specific exceptions
        } catch (e: HttpException) {
            when (e.code()) {
                400 -> Log.e("QuizRepository", "Bad Request: ${e.message()}")
                401 -> Log.e("QuizRepository", "Unauthorized")
                404 -> Log.e("QuizRepository", "Not Found")
                500 -> Log.e("QuizRepository", "Server Error")
                else -> Log.e("QuizRepository", "HTTP error ${e.code()}: ${e.message()}")
            }
            cacheUnsentScore(context, score)
            Result.failure(e)
        // Catching network exceptions
        } catch (e: IOException) {
            Log.e("QuizRepository", "Network error", e)
            cacheUnsentScore(context, score)
            Result.failure(e)
        // Catching generic exceptions
        } catch (e: Exception) {
            Log.e("QuizRepository", "Unexpected error", e)
            cacheUnsentScore(context, score)
            Result.failure(e)
        }
    }

    suspend fun getScore(deviceId: String, language: String, quizName: String): Result<Int?> {
        return try {
            // Calling getScore from api service
            val response = apiService.getScore(deviceId, language, quizName)
            // Logging response
            if (response.isSuccessful) {
                val score = response.body()?.score
                Result.success(score)
            } else {
                Log.e("QuizRepository", "Server error: ${response.code()} ${response.message()}")
                Result.failure(Exception("Server error: ${response.code()} ${response.message()}"))
            }
        // Catching HTTP specific exceptions
        } catch (e: HttpException) {
            when (e.code()) {
                400 -> Log.e("QuizRepository", "Bad Request: ${e.message()}")
                401 -> Log.e("QuizRepository", "Unauthorized")
                404 -> Log.e("QuizRepository", "Not Found")
                500 -> Log.e("QuizRepository", "Server Error")
                else -> Log.e("QuizRepository", "HTTP error ${e.code()}: ${e.message()}")
            }
            Result.failure(e)
        // Catching network exceptions
        } catch (e: IOException) {
            Log.e("QuizRepository", "Network error", e)
            Result.failure(e)
        // Catching generic exceptions
        } catch (e: Exception) {
            Log.e("QuizRepository", "Unexpected error", e)
            Result.failure(e)
        }
    }

    private fun cacheUnsentScore(context: Context, score: QuizScore) {
        // Getting or creating shared preferences object
        val prefs = context.getSharedPreferences("unsent_scores", Context.MODE_PRIVATE)
        // Gson object
        val gson = Gson()
        // Current list of pending scores
        val current = prefs.getString("pending", null)

        // If there are pending scores, get the list of them. Otherwise create a new list.
        val list = if (current != null) {
            gson.fromJson(current, Array<QuizScore>::class.java).toMutableList()
        } else mutableListOf()

        // Add score to list of pending scores
        list.add(score)
        // Put pending list into sharedprefs object
        prefs.edit().putString("pending", gson.toJson(list)).apply()
    }
}