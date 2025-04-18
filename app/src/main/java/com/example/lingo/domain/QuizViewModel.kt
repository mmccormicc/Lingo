package com.example.lingo.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lingo.R
import com.example.lingo.data.Quiz
import com.example.lingo.data.QuizQuestion


class QuizViewModel(): ViewModel() {

    // Spanish quizzes
    var spanishQuiz1: Quiz =
        Quiz(listOf(
            QuizQuestion("What is house in Spanish?", 0, listOf("Casa", "Dasa", "Masa", "Basa")),
            QuizQuestion("What is mouse in Spanish?", 0, listOf("Casa", "Dasa", "Masa", "Basa")),
            QuizQuestion("What is blouse in Spanish?", 0, listOf("Casa", "Dasa", "Masa", "Basa")),
            QuizQuestion("What is louse in Spanish?", 0, listOf("Casa", "Dasa", "Masa", "Basa")),
            QuizQuestion("What is grouse in Spanish?", 0, listOf("Casa", "Dasa", "Masa", "Basa")),
        ))
    var spanishQuiz2: Quiz =
        Quiz(listOf(QuizQuestion("What is dog in Spanish?", 0, listOf("Perro"))))

    var spanishQuizzes: List<Quiz> = listOf(spanishQuiz1, spanishQuiz2)

    var questionNumber: Int by mutableStateOf(0)
    var correctAnswers: Int by mutableStateOf(0)
    var quiz: Quiz by mutableStateOf(Quiz(listOf()))
    var numQuestions: Int by mutableStateOf(0)

    fun resetViewModel() {
        questionNumber = 0
        correctAnswers = 0
    }

    fun nextQuestion(answerIndex : Int): Boolean {
        if (answerIndex == quiz.questions[questionNumber].answer) {
            correctAnswers++
        }
        if (questionNumber < numQuestions - 1) {
            questionNumber++
            return false
        }
        return true
    }

    fun setQuiz(quizNumber: Int, languageName: String) {
        val quizzes = when (languageName.lowercase()) {
            "{spanish}" -> spanishQuizzes
            "{french}" -> spanishQuizzes
            "{german}" -> spanishQuizzes
            "{italian}" -> spanishQuizzes
            else -> spanishQuizzes
        } as List<Quiz>

        quiz = quizzes[quizNumber]

        numQuestions = quiz.questions.size
    }

    fun getQuestion(): QuizQuestion {
        return quiz.questions[questionNumber]
    }

}