package com.example.lingo.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lingo.data.Flashcard
import com.example.lingo.data.FlashcardCollection
import com.example.lingo.data.Quiz
import kotlin.random.Random


class FlashcardViewModel(): ViewModel() {

    var spanishNounCards: FlashcardCollection = FlashcardCollection(mutableListOf(
        Flashcard("Man", "Hombre"),
        Flashcard("Woman", "Mujer"),
        Flashcard("House", "Casa"),
        Flashcard("Dog", "Perro"),
    ))

    var spanishVerbCards: FlashcardCollection = FlashcardCollection(mutableListOf(
        Flashcard("To run", "Correr"),
        Flashcard("To sleep", "Dormir"),
        Flashcard("To eat", "Comer"),
        Flashcard("To work", "Trabajar"),
    ))

    var spanishCards: List<FlashcardCollection> = listOf(spanishNounCards, spanishVerbCards)


    var frenchNounCards: FlashcardCollection = FlashcardCollection(mutableListOf(
        Flashcard("Man", "Homme"),
        Flashcard("Woman", "Femme"),
        Flashcard("House", "Maison"),
        Flashcard("Dog", "Chien"),
    ))

    var frenchVerbCards: FlashcardCollection = FlashcardCollection(mutableListOf(
        Flashcard("To run", "Courir"),
        Flashcard("To sleep", "Dormir"),
        Flashcard("To eat", "Manger"),
        Flashcard("To work", "Travailler"),
    ))

    var frenchCards: List<FlashcardCollection> = listOf(frenchNounCards, frenchVerbCards)


    var germanNounCards: FlashcardCollection = FlashcardCollection(mutableListOf(
        Flashcard("Man", "Mann"),
        Flashcard("Woman", "Frau"),
        Flashcard("House", "Haus"),
        Flashcard("Dog", "Hund"),
    ))

    var germanVerbCards: FlashcardCollection = FlashcardCollection(mutableListOf(
        Flashcard("To run", "Laufen"),
        Flashcard("To sleep", "Schlafen"),
        Flashcard("To eat", "Essen"),
        Flashcard("To work", "Arbeiten"),
    ))

    var germanCards: List<FlashcardCollection> = listOf(germanNounCards, germanVerbCards)


    var italianNounCards: FlashcardCollection = FlashcardCollection(mutableListOf(
        Flashcard("Man", "Uomo"),
        Flashcard("Woman", "Donna"),
        Flashcard("House", "Casa"),
        Flashcard("Dog", "Cagna"),
    ))

    var italianVerbCards: FlashcardCollection = FlashcardCollection(mutableListOf(
        Flashcard("To run", "Correre"),
        Flashcard("To sleep", "Dormire"),
        Flashcard("To eat", "Mangiare"),
        Flashcard("To work", "Lavorare"),
    ))

    var italianCards: List<FlashcardCollection> = listOf(italianNounCards, italianVerbCards)


    // Holds current quiz
    var currentLanguageFlashcards: FlashcardCollection by mutableStateOf(FlashcardCollection(mutableListOf()))

    // Holds list of questions not displayed in this rotation
    var unseenCards: MutableList<Flashcard> = mutableListOf()

    // Holds list of questions that have been displayed this rotation
    var seenCards: MutableList<Flashcard> = mutableListOf()

    // Holds if picture match has been initialized
    var initialized: Boolean by mutableStateOf(false)

    // Holds current question. Starts as error question before random question is generated.
    var currentCard: Flashcard by mutableStateOf(Flashcard("Error", "Error"))

    // Holds number of questions displayed, used to identify questions within composable
    var currentCardIndex: Int by mutableIntStateOf(0)

    fun nextCard() {
        println("NEXT CARD")
        // Questions remaining
        if (unseenCards.size > 1) {
            println("Has next question")
            // Get random index in range of unseen question list size
            var nextCardIndex = Random.nextInt(0, unseenCards.size)

            val nextCard = unseenCards.get(nextCardIndex)
            // Set current question from index
            currentCard = Flashcard(nextCard.englishSide, nextCard.translatedSide)
            // Adding to seen questions
            seenCards.add(currentCard)
            // Removing from unseen questions
            unseenCards.removeAt(nextCardIndex)
            // Updating current question index
            currentCardIndex++
        // All questions removed
        } else {
            // Set to only question left
            currentCard = unseenCards.get(0)
            // Removing last question
            unseenCards.removeAt(0)
            // Adding all questions back to unseen questions by creating copy of seen questions
            unseenCards = seenCards.toMutableList()
            // Clearing seen questions
            seenCards.clear()
            // Adding current question to seen questions
            seenCards.add(currentCard)
            // Updating curent question index
            currentCardIndex++
        }
    }

    // Set current quiz to be displayed
    fun setFlashcards(flashcardNumber: Int, languageName: String) {
        // Getting list of quizzes depending on selected language
        val languageFlashcards = when (languageName.lowercase()) {
            "{spanish}" -> spanishCards
            "{french}" -> frenchCards
            "{german}" -> germanCards
            "{italian}" -> italianCards
            else -> spanishCards
        } as List<FlashcardCollection>

        currentLanguageFlashcards = languageFlashcards[flashcardNumber]

        unseenCards = currentLanguageFlashcards.flashcards


    }


}