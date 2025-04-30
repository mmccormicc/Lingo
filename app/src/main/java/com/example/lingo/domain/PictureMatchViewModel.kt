package com.example.lingo.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lingo.R
import com.example.lingo.data.PictureMatchQuestion
import kotlin.random.Random


class PictureMatchViewModel(): ViewModel() {

    var spanishQuestions: List<PictureMatchQuestion> = mutableListOf(
            PictureMatchQuestion(R.drawable.man, 0, listOf("Hombre", "Hombro", "Mujer", "Hambre")),
            PictureMatchQuestion(R.drawable.woman, 2, listOf("Nino", "Hombre", "Mujer", "Perro")),
            PictureMatchQuestion(R.drawable.dog, 1, listOf("Gato", "Perro", "Caballo", "Lobo")),
            PictureMatchQuestion(R.drawable.house, 3, listOf("Corcho", "Caso", "Escuela", "Casa"))
    )

    var frenchQuestions: List<PictureMatchQuestion> = mutableListOf(
        PictureMatchQuestion(R.drawable.man, 0, listOf("Homme", "Chat", "Femme", "Garcon")),
        PictureMatchQuestion(R.drawable.woman, 2, listOf("Chienne", "Fille", "Femme", "Homme")),
        PictureMatchQuestion(R.drawable.dog, 0, listOf("Chien", "Chat", "Loup", "Chatte")),
        PictureMatchQuestion(R.drawable.house, 1, listOf("Ecole", "Maison", "Chambre", "Manoir"))
    )

    var germanQuestions: List<PictureMatchQuestion> = mutableListOf(
        PictureMatchQuestion(R.drawable.man, 3, listOf("Papa", "Frau", "Junge", "Mann")),
        PictureMatchQuestion(R.drawable.woman, 2, listOf("Mama", "Madchen", "Frau", "Frosch")),
        PictureMatchQuestion(R.drawable.dog, 1, listOf("Wolf", "Hund", "Katze", "Pferd")),
        PictureMatchQuestion(R.drawable.house, 0, listOf("Haus", "Zimmer", "Schule", "Villa"))
    )

    var italianQuestions: List<PictureMatchQuestion> = mutableListOf(
        PictureMatchQuestion(R.drawable.man, 0, listOf("Uomo", "Donna", "Ragazzo", "Nonno")),
        PictureMatchQuestion(R.drawable.woman, 3, listOf("Uomo", "Ragazza", "Nonna", "Donna")),
        PictureMatchQuestion(R.drawable.dog, 3, listOf("Cavalla", "Lupa", "Gatta", "Cagna")),
        PictureMatchQuestion(R.drawable.house, 1, listOf("Palazzo", "Casa", "Scuola", "Camera"))
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
        if (unseenQuestions.size > 1) {
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
            // Set to only question left
            currentQuestion = unseenQuestions.get(0)
            // Removing last question
            unseenQuestions.removeAt(0)
            // Adding all questions back to unseen questions by creating copy of seen questions
            unseenQuestions = seenQuestions.toMutableList()
            // Clearing seen questions
            seenQuestions.clear()
            // Adding current question to seen questions
            seenQuestions.add(currentQuestion)
            // Updating curent question index
            currentQuestionIndex++
        }

        print(seenQuestions)
    }

    // Set picture questions to be displayed
    fun setQuestions(languageName: String) {
        // Getting list of questions depending on selected language
        unseenQuestions = when (languageName.lowercase()) {
            "{spanish}" -> spanishQuestions
            "{french}" -> frenchQuestions
            "{german}" -> germanQuestions
            "{italian}" -> italianQuestions
            else -> spanishQuestions
        } as MutableList<PictureMatchQuestion>
    }


}