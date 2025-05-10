package com.example.lingo.data

import kotlinx.serialization.Serializable

// This is serializable so it can be sent over network
@Serializable
data class QuizScore(
    // ID of device that achieved score
    val deviceId: String,
    // Language of quiz
    val language: String,
    // Name of quiz
    val quizName: String,
    // Score
    val score: Int
)