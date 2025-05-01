package com.example.lingo.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lingo.R
import com.example.lingo.data.PictureMatchQuestion
import com.example.lingo.data.Quiz
import com.example.lingo.data.QuizQuestion
import kotlin.random.Random


class MissingWordsViewModel(): ViewModel(), BaseQuestionsViewModel {

    var spanishQuestions: MutableList<QuizQuestion> = mutableListOf(
            QuizQuestion("Él (corrió/corre) en el parque ayer.", 0, listOf("corrió", "corre")),
            QuizQuestion("Ellos (viven/vivirán) en Madrid el próximo año.", 1, listOf("viven", "vivirán")),
            QuizQuestion("Yo (he leído/leo) muchos libros este mes.", 0, listOf("he leído", "leo")),
            QuizQuestion("María (bailaba/baila) mientras llovía.", 0, listOf("bailaba", "baila"))
    )

    var frenchQuestions: MutableList<QuizQuestion> = mutableListOf(
        QuizQuestion("Il (est allé/va) au marché ce matin.", 0, listOf("est allé", "va")),
        QuizQuestion("Nous (mangeons/mangerons) chez nos amis demain.", 1, listOf("mangeons", "mangerons")),
        QuizQuestion("Je (ferais/fais) du sport si j'avais le temps.", 0, listOf("ferais", "fais")),
        QuizQuestion("Vous (aviez/avez) oublié vos clés hier.", 0, listOf("aviez", "avez"))
    )

    val italianQuestions = mutableListOf(
        QuizQuestion("Io (faccio/farei) ginnastica se avessi tempo.", 1, listOf("faccio", "farei")),
        QuizQuestion("Noi (mangiamo/mangeremo) la pizza domani sera.", 1, listOf("mangiamo", "mangeremo")),
        QuizQuestion("Voi (avevate/avete) dimenticato le chiavi ieri.", 0, listOf("avevate", "avete")),
        QuizQuestion("(Andremo/Andavamo) al mare questo weekend.", 0, listOf("Andremo", "Andavamo"))
    )

    val germanQuestions = mutableListOf(
        QuizQuestion("Ich (würde machen/mache) mehr Sport, wenn ich Zeit hätte.", 0, listOf("würde machen", "mache")),
        QuizQuestion("Wir (essen/werden essen) morgen bei Freunden.", 1, listOf("essen", "werden essen")),
        QuizQuestion("Wir (gehen/werden gehen) am Wochenende ins Kino.", 1, listOf("gehen", "werden gehen")),
        QuizQuestion("(Hast du/Bringst du) den Bus heute Morgen genommen?", 0, listOf("Hast du", "Bringst du"))
    )

    // Holds list of questions not displayed in this rotation
    var unseenQuestions: MutableList<QuizQuestion> = mutableListOf()

    // Holds list of questions that have been displayed this rotation
    var seenQuestions: MutableList<QuizQuestion> = mutableListOf()

    // Holds if picture match has been initialized
    var initialized: Boolean by mutableStateOf(false)

    // Holds current question. Starts as error question before random question is generated.
    var currentQuestion: QuizQuestion by mutableStateOf(QuizQuestion("Error", 0, listOf("Error", "Error")))

    // Holds number of questions displayed, used to identify questions within composable
    var currentQuestionIndex: Int by mutableIntStateOf(0)

    override fun nextQuestion() {
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
        } as MutableList<QuizQuestion>
    }


}