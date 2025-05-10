package com.example.lingo.data

data class QuizQuestion(
    // Question text
    val question: String,
    // Index of correct answer
    val answer: Int,
    // Answer options
    val options: List<String>
)