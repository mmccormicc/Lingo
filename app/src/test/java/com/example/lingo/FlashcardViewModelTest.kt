package com.example.lingo

import com.example.lingo.data.Flashcard
import com.example.lingo.domain.FlashcardViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FlashcardViewModelTest {

    private lateinit var viewModel: FlashcardViewModel

    @Before
    fun setUp() {
        // Initialize the ViewModel before each test
        viewModel = FlashcardViewModel()
        // Set some mock flashcards for testing (example: Spanish Nouns)
        viewModel.setFlashcards(0, "{spanish}")
    }

    @Test
    fun setFlashcards_SetToSpanishNounCards_HasCorrectNumberOfCards() {
        // Assert that the unseenCards list has the same size as the selected flashcard category
        assertEquals(viewModel.spanishNounCards.flashcards.size, viewModel.unseenCards.size)
    }

    @Test
    fun nextCard_MoreThanOneCardLeft_GoesToNextCard() {
        // Initial values to verify against
        val initialUnseenSize = viewModel.unseenCards.size
        val initialSeenSize = viewModel.seenCards.size

        // Call nextCard() to simulate showing a flashcard
        viewModel.nextCard()

        // Assert that the unseen cards list size has decreased by 1
        assertEquals(initialUnseenSize - 1, viewModel.unseenCards.size)

        // Assert that the seen cards list size has increased by 1
        assertEquals(initialSeenSize + 1, viewModel.seenCards.size)

        // Assert that the current card has been updated
        assertNotEquals(Flashcard("Error", "Error"), viewModel.currentCard)
    }

    @Test
    fun nextCard_OneCardLeft_ResetsCardsThenGoesNext() {
        // Looping through all but one of the flashcards
        for (i in 1 until viewModel.spanishNounCards.flashcards.size) {
            viewModel.nextCard()
        }

        // Asserting there is 1 card left
        assertEquals(1, viewModel.unseenCards.size)

        // Going to next card which should reset
        viewModel.nextCard()

        // Assert that the last card was transferred to seen cards
        assertEquals(1, viewModel.seenCards.size)

        // Assert that unseen cards have all but one card
        assertEquals(viewModel.spanishNounCards.flashcards.size - 1, viewModel.unseenCards.size)

        // Assert that the current card has been updated
        assertNotEquals(Flashcard("Error", "Error"), viewModel.currentCard)
    }

}