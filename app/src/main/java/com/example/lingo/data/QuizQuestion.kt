package com.example.lingo.data

data class QuizQuestion(
    val question: String,
    val answer: Int,
    val options: List<String>
)