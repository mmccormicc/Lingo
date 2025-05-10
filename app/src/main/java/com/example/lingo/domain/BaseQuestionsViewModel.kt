package com.example.lingo.domain

// This lets color change button call nextQuestion of both picture match and missing words view models
interface BaseQuestionsViewModel {
    fun nextQuestion()
}