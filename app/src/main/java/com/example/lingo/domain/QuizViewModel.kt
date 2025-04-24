package com.example.lingo.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
            QuizQuestion("What is house in Spanish?", 2, listOf("Carro", "Gato", "Casa", "Arbol")),
            QuizQuestion("What is man in Spanish?", 1, listOf("Mujer", "Hombre", "Nina", "Gato")),
            QuizQuestion("What is woman in Spanish?", 2, listOf("Hombre", "Nino", "Mujer", "Perro")),
            QuizQuestion("What is dog in Spanish?", 1, listOf("Gato", "Perro", "Pajaro", "Vaca")),
        ))
    var spanishQuiz2: Quiz =
        Quiz(listOf(
            QuizQuestion("Which verb means 'to run' in Spanish?", 0, listOf("Correr", "Dormir", "Trabajar", "Comer")),
            QuizQuestion("Which verb means 'to sleep' in Spanish?", 3, listOf("Trabajar", "Comer", "Correr", "Dormir")),
            QuizQuestion("Which verb means 'to eat' in Spanish?", 0, listOf("Comer", "Trabajar", "Dormir", "Correr")),
            QuizQuestion("Which verb means 'to work' in Spanish?", 2, listOf("Correr", "Comer", "Trabajar", "Dormir")),
        ))

    var spanishQuizzes: List<Quiz> = listOf(spanishQuiz1, spanishQuiz2)

    // Holds current question number
    var questionNumber: Int by mutableIntStateOf(0)
    // Holds number of answers gotten as correct
    var correctAnswers: Int by mutableIntStateOf(0)
    // Holds current quiz
    var quiz: Quiz by mutableStateOf(Quiz(listOf()))
    // Holds number of questions on quiz
    var numQuestions: Int by mutableIntStateOf(0)

    fun resetViewModel() {
        questionNumber = 0
        correctAnswers = 0
    }

    // Go to next question in quiz. Returns true if quiz is over.
    fun nextQuestion(answerIndex : Int): Boolean {
        /// If answer was correct, increase correct answers
        if (answerIndex == quiz.questions[questionNumber].answer) {
            correctAnswers++
        }
        // If not last question
        if (questionNumber < numQuestions - 1) {
            questionNumber++
            return false
        }
        return true
    }

    // Set current quiz to be displayed
    fun setQuiz(quizNumber: Int, languageName: String) {
        // Getting list of quizzes depending on selected language
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

    // Get current question
    fun getQuestion(): QuizQuestion {
        return quiz.questions[questionNumber]
    }

}