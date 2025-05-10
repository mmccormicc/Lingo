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

    // Collections of flashcards grouped by category

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


    // Holds current flashcard collection
    var currentLanguageFlashcards: FlashcardCollection by mutableStateOf(FlashcardCollection(mutableListOf()))

    // Holds list of cards not displayed in this rotation
    var unseenCards: MutableList<Flashcard> = mutableListOf()

    // Holds list of cards that have been displayed this rotation
    var seenCards: MutableList<Flashcard> = mutableListOf()

    // Holds if picture match has been initialized
    var initialized: Boolean by mutableStateOf(false)

    // Holds current card. Starts as error card before random card is generated.
    var currentCard: Flashcard by mutableStateOf(Flashcard("Error", "Error"))

    // Holds number of cards displayed, used to identify cards within composable
    var currentCardIndex: Int by mutableIntStateOf(0)

    fun nextCard() {
        // cards remaining
        if (unseenCards.size > 1) {
            println("Has next card")
            // Get random index in range of unseen card list size
            var nextCardIndex = Random.nextInt(0, unseenCards.size)

            val nextCard = unseenCards.get(nextCardIndex)
            // Set current card from index
            currentCard = Flashcard(nextCard.englishSide, nextCard.translatedSide)
            // Adding to seen cards
            seenCards.add(currentCard)
            // Removing from unseen cards
            unseenCards.removeAt(nextCardIndex)
            // Updating current card index
            currentCardIndex++
        // All cards removed
        } else {
            // Set to only card left
            currentCard = unseenCards.get(0)
            // Removing last card
            unseenCards.removeAt(0)
            // Adding all cards back to unseen cards by creating copy of seen cards
            unseenCards = seenCards.toMutableList()
            // Clearing seen cards
            seenCards.clear()
            // Adding current card to seen cards
            seenCards.add(currentCard)
            // Updating current card index
            currentCardIndex++
        }
    }

    // Set current flashcard collection to be displayed
    fun setFlashcards(flashcardNumber: Int, languageName: String) {
        // Getting list of flashcard collections depending on selected language
        val languageFlashcards = when (languageName.lowercase()) {
            "{spanish}" -> spanishCards
            "{french}" -> frenchCards
            "{german}" -> germanCards
            "{italian}" -> italianCards
            else -> spanishCards
        }

        // Getting list of flashcards based on selected flashcard category
        currentLanguageFlashcards = languageFlashcards[flashcardNumber]

        // Setting unseenCards to initially include all cards
        unseenCards = currentLanguageFlashcards.flashcards.toMutableList()


    }


}