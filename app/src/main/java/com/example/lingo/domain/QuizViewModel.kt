package com.example.lingo.domain

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lingo.data.Quiz
import com.example.lingo.data.QuizQuestion
import com.example.lingo.data.QuizScore
import com.example.lingo.network.QuizRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class QuizViewModel(private val repository: QuizRepository) : ViewModel() {

    // Spanish quizzes
    var spanishQuiz1: Quiz =
        Quiz(
            listOf(
                QuizQuestion(
                    "What is house in Spanish?",
                    2,
                    listOf("Carro", "Gato", "Casa", "Arbol")
                ),
                QuizQuestion(
                    "What is man in Spanish?",
                    1,
                    listOf("Mujer", "Hombre", "Nina", "Gato")
                ),
                QuizQuestion(
                    "What is woman in Spanish?",
                    2,
                    listOf("Hombre", "Nino", "Mujer", "Perro")
                ),
                QuizQuestion(
                    "What is dog in Spanish?",
                    1,
                    listOf("Gato", "Perro", "Pajaro", "Vaca")
                ),
            )
        )
    var spanishQuiz2: Quiz =
        Quiz(
            listOf(
                QuizQuestion(
                    "Which verb means 'to run' in Spanish?",
                    0,
                    listOf("Correr", "Dormir", "Trabajar", "Comer")
                ),
                QuizQuestion(
                    "Which verb means 'to sleep' in Spanish?",
                    3,
                    listOf("Trabajar", "Comer", "Correr", "Dormir")
                ),
                QuizQuestion(
                    "Which verb means 'to eat' in Spanish?",
                    0,
                    listOf("Comer", "Trabajar", "Dormir", "Correr")
                ),
                QuizQuestion(
                    "Which verb means 'to work' in Spanish?",
                    2,
                    listOf("Correr", "Comer", "Trabajar", "Dormir")
                ),
            )
        )

    var spanishQuizzes: List<Quiz> = listOf(spanishQuiz1, spanishQuiz2)

    // French quizzes
    var frenchQuiz1: Quiz =
        Quiz(
            listOf(
                QuizQuestion(
                    "What is house in French?",
                    1,
                    listOf("Ecole", "Maison", "Chambre", "Manoir")
                ),
                QuizQuestion(
                    "What is man in French?",
                    0,
                    listOf("Homme", "Chat", "Femme", "Garcon")
                ),
                QuizQuestion(
                    "What is woman in French?",
                    2,
                    listOf("Chienne", "Fille", "Femme", "Homme")
                ),
                QuizQuestion(
                    "What is dog (masculine) in French?",
                    0,
                    listOf("Chien", "Chat", "Chienne", "Chatte")
                ),
            )
        )
    var frenchQuiz2: Quiz =
        Quiz(
            listOf(
                QuizQuestion(
                    "Which verb means 'to run' in French?",
                    0,
                    listOf("Courir", "Dormir", "Travailler", "Manger")
                ),
                QuizQuestion(
                    "Which verb means 'to sleep' in French",
                    0,
                    listOf("Dormir", "Manger", "Travailler", "Courir")
                ),
                QuizQuestion(
                    "Which verb means 'to eat' in French?",
                    3,
                    listOf("Courir", "Travailler", "Dormir", "Manger")
                ),
                QuizQuestion(
                    "Which verb means 'to work' in French?",
                    1,
                    listOf("Courir", "Travailler", "Manger", "Dormir")
                ),
            )
        )

    var frenchQuizzes: List<Quiz> = listOf(frenchQuiz1, frenchQuiz2)


    // German quizzes
    var germanQuiz1: Quiz =
        Quiz(
            listOf(
                QuizQuestion(
                    "What is house in German?",
                    0,
                    listOf("Haus", "Zimmer", "Schule", "Villa")
                ),
                QuizQuestion("What is man in German?", 3, listOf("Papa", "Frau", "Junge", "Mann")),
                QuizQuestion(
                    "What is woman in German?",
                    2,
                    listOf("Mama", "Madchen", "Frau", "Frosch")
                ),
                QuizQuestion("What is dog in German?", 1, listOf("Wolf", "Hund", "Katze", "Pferd")),
            )
        )
    var germanQuiz2: Quiz =
        Quiz(
            listOf(
                QuizQuestion(
                    "Which verb means 'to run' in German?",
                    2,
                    listOf("Schlafen", "Arbeiten", "Laufen", "Essen")
                ),
                QuizQuestion(
                    "Which verb means 'to sleep' in German",
                    0,
                    listOf("Schlafen", "Laufen", "Essen", "Arbeiten")
                ),
                QuizQuestion(
                    "Which verb means 'to eat' in German?",
                    3,
                    listOf("Arbeiten", "Laufen", "Schlafen", "Essen")
                ),
                QuizQuestion(
                    "Which verb means 'to work' in German?",
                    1,
                    listOf("Essen", "Arbeiten", "Schlafen", "Laufen")
                ),
            )
        )

    var germanQuizzes: List<Quiz> = listOf(germanQuiz1, germanQuiz2)


    // Italian quizzes
    var italianQuiz1: Quiz =
        Quiz(
            listOf(
                QuizQuestion(
                    "What is house in Italian?",
                    1,
                    listOf("Palazzo", "Casa", "Scuola", "Camera")
                ),
                QuizQuestion(
                    "What is man in Italian?",
                    0,
                    listOf("Uomo", "Donna", "Ragazzo", "Nonno")
                ),
                QuizQuestion(
                    "What is woman in Italian?",
                    3,
                    listOf("Uomo", "Ragazza", "Nonna", "Donna")
                ),
                QuizQuestion(
                    "What is dog (feminine) in Italian?",
                    3,
                    listOf("Cavalla", "Lupa", "Gatta", "Cagna")
                ),
            )
        )
    var italianQuiz2: Quiz =
        Quiz(
            listOf(
                QuizQuestion(
                    "Which verb means 'to run' in Italian?",
                    3,
                    listOf("Lavorare", "Mangiare", "Dormire", "Correre")
                ),
                QuizQuestion(
                    "Which verb means 'to sleep' in Italian",
                    0,
                    listOf("Dormire", "Lavorare", "Essen", "Mangiare")
                ),
                QuizQuestion(
                    "Which verb means 'to eat' in Italian?",
                    2,
                    listOf("Lavorare", "Correre", "Mangiare", "Dormire")
                ),
                QuizQuestion(
                    "Which verb means 'to work' in Italian?",
                    3,
                    listOf("Dormire", "Mangiare", "Correre", "Lavorare")
                ),
            )
        )

    var italianQuizzes: List<Quiz> = listOf(italianQuiz1, italianQuiz2)

    // Holds current question number
    var questionNumber: Int by mutableIntStateOf(0)

    // Holds number of answers gotten as correct
    var correctAnswers: Int by mutableIntStateOf(0)

    // Holds current quiz being viewed
    var quiz: Quiz by mutableStateOf(Quiz(listOf()))

    // Holds number of questions on quiz
    var numQuestions: Int by mutableIntStateOf(0)
    

    // Go to next question in quiz. Returns true if quiz is over.
    fun nextQuestion(answerIndex: Int): Boolean {
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

    fun getNumQuestions(quizNumber: Int, languageName: String): Int {
        // Getting list of quizzes depending on selected language
        val quizzes = when (languageName.lowercase()) {
            "{spanish}" -> spanishQuizzes
            "{french}" -> frenchQuizzes
            "{german}" -> germanQuizzes
            "{italian}" -> italianQuizzes
            else -> spanishQuizzes
        } as List<Quiz>

        // Getting quiz
        quiz = quizzes[quizNumber]

        // Returning number of questions in quiz
        return quiz.questions.size
    }

    // Set current quiz to be displayed
    fun setQuiz(quizNumber: Int, languageName: String) {
        // Getting list of quizzes depending on selected language
        val quizzes = when (languageName.lowercase()) {
            "{spanish}" -> spanishQuizzes
            "{french}" -> frenchQuizzes
            "{german}" -> germanQuizzes
            "{italian}" -> italianQuizzes
            else -> spanishQuizzes
        } as List<Quiz>

        quiz = quizzes[quizNumber]

        numQuestions = quiz.questions.size
    }

    // Get current question
    fun getQuestion(): QuizQuestion {
        return quiz.questions[questionNumber]
    }

    // Submit new quiz score to server
    fun submitScore(deviceId: String, language: String, quizName: String, context: Context) {
        val score = QuizScore(deviceId, language, quizName, correctAnswers)
        Log.d("Score Info", score.toString())
        viewModelScope.launch {
            repository.submitScore(score, context)
        }
    }

    private val _scoresMap = MutableStateFlow<Map<String, Int>>(emptyMap())
    val scoresMap: StateFlow<Map<String, Int>> = _scoresMap

    // Retrieve a quiz score based on deviceId, language, and quiz nae
    fun getScore(deviceId: String, language: String, quizName: String) {
        viewModelScope.launch {
            val result = repository.getScore(deviceId, language, quizName)
            if (result.isSuccess) {
                // Updating map of scores with retrieved score
                result.getOrNull()?.let { score ->
                    _scoresMap.update { it + (quizName to score) }
                }
            } else if (result.isFailure) {
                Log.e("QuizViewModel", "Failed to fetch score", result.exceptionOrNull())
            }
        }
    }

}


// Factory for creating view model with access to repository
class QuizViewModelFactory(
    private val repository: QuizRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            return QuizViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}