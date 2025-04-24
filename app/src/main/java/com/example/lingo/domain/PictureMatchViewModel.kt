package com.example.lingo.domain

import android.graphics.Picture
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lingo.R
import com.example.lingo.data.PictureMatchQuestion
import com.example.lingo.data.Quiz
import com.example.lingo.data.QuizQuestion
import kotlin.random.Random


class PictureMatchViewModel(): ViewModel() {

    var spanishQuestions: List<PictureMatchQuestion> = mutableListOf(
            PictureMatchQuestion(R.drawable.man, 0, listOf("Hombre", "Hombro", "Mujer", "Hambre")),
            PictureMatchQuestion(R.drawable.woman, 2, listOf("Nino", "Hombre", "Mujer", "Perro")),
            PictureMatchQuestion(R.drawable.dog, 1, listOf("Gato", "Perro", "Caballo", "Lobo"))
    )

    // Holds list of questions not displayed in this rotation
    var unseenQuestions: MutableList<PictureMatchQuestion> = mutableListOf()

    // Holds list of questions that have been displayed this rotation
    var seenQuestions: MutableList<PictureMatchQuestion> = mutableListOf()

    // Holds if picture match has been initialized
    var initialized: Boolean by mutableStateOf(false)

    // Holds current question. Starts as error question before random question is generated.
    var currentQuestion: PictureMatchQuestion by mutableStateOf(PictureMatchQuestion(R.drawable.man, 0, listOf("Error", "Error", "Error", "Error")))

    // Holds number of questions displayed, used to identify questions within composable
    var currentQuestionIndex: Int by mutableIntStateOf(0)

    fun nextQuestion() {
        // Questions remaining
        if (unseenQuestions.isNotEmpty()) {
            println("Has next question")
            // Get random index in range of unseen question list size
            var nextQuestionIndex = Random.nextInt(0, unseenQuestions.size)
            // Set current question from index
            currentQuestion = unseenQuestions.get(nextQuestionIndex)
            // Adding to seen questions
            seenQuestions.add(currentQuestion)
            // Removing from unseen questions
            unseenQuestions.removeAt(nextQuestionIndex)
            // Updating current question index
            currentQuestionIndex++
        // All questions removed
        } else {
            println("All out of questions")
            // Adding all questions back to unseen questions by creating copy of seen questions
            unseenQuestions = seenQuestions.toMutableList()
            // Clearing seen questions
            seenQuestions.clear()
            // Displaying next question now that unseen questions is reset
            nextQuestion()
        }

        print(seenQuestions)
    }

    // Set picture questions to be displayed
    fun setQuestions(languageName: String) {
        // Getting list of questions depending on selected language
        unseenQuestions = when (languageName.lowercase()) {
            "{spanish}" -> spanishQuestions
            "{french}" -> spanishQuestions
            "{german}" -> spanishQuestions
            "{italian}" -> spanishQuestions
            else -> spanishQuestions
        } as MutableList<PictureMatchQuestion>
    }


}