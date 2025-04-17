package com.example.lingo.datahelper

import com.example.lingo.data.Quiz
import com.example.lingo.data.QuizQuestion

class QuizHelper() {
    companion object {
        var spanishQuiz1: Quiz = Quiz(listOf(QuizQuestion("What is house in Spanish?", 0, listOf("Casa"))))
        var spanishQuiz2: Quiz = Quiz(listOf(QuizQuestion("What is dog in Spanish?", 0, listOf("Perro"))))

        var spanishQuizzes: List<Quiz> = listOf(spanishQuiz1, spanishQuiz2)
    }
}