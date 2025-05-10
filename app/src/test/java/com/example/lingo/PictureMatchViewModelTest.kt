package com.example.lingo

import com.example.lingo.data.PictureMatchQuestion
import com.example.lingo.domain.PictureMatchViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PictureMatchViewModelTest {

    private lateinit var viewModel: PictureMatchViewModel

    @Before
    fun setUp() {
        // Initialize the ViewModel before each test
        viewModel = PictureMatchViewModel()
        // Set some mock questions for testing
        viewModel.setQuestions("{spanish}")
    }

    @Test
    fun setQuestions_SetToSpanishQuestions_HasCorrectNumberOfQuestions() {
        assertEquals(viewModel.spanishQuestions.size, viewModel.unseenQuestions.size)
    }

    @Test
    fun nextQuestion_MoreThanOneQuestionLeft_GoesToNextQuestion() {

        // Initial values to verify against
        val initialUnseenSize = viewModel.unseenQuestions.size
        val initialSeenSize = viewModel.seenQuestions.size

        // Call nextQuestion() to simulate showing a question
        viewModel.nextQuestion()

        // Assert that the unseen question list size has decreased by 1
        assertEquals(initialUnseenSize - 1, viewModel.unseenQuestions.size)

        // Assert that the seen question list size has increased by 1
        assertEquals(initialSeenSize + 1, viewModel.seenQuestions.size)

        // Assert that the current question has been updated
        assertNotEquals(PictureMatchQuestion(R.drawable.man, 0, listOf("Error", "Error", "Error", "Error")), viewModel.currentQuestion)
    }

    @Test
    fun nextQuestion_OneQuestionLeft_ResetsQuestionsThenGoesNext() {
        // Looping through all but one of questions
        for (i in 1..viewModel.spanishQuestions.size - 1) {
            viewModel.nextQuestion()
        }

        // Asserting there is 1 question left
        assertEquals(1, viewModel.unseenQuestions.size)

        viewModel.nextQuestion()

        // Assert last question was transferred to seen questions
        assertEquals(1, viewModel.seenQuestions.size)

        // Assert unseen questions has all but one question
        assertEquals(viewModel.spanishQuestions.size - 1, viewModel.unseenQuestions.size)

        // Assert that the current question has been updated
        assertNotEquals(PictureMatchQuestion(R.drawable.man, 0, listOf("Error", "Error", "Error", "Error")), viewModel.currentQuestion)
    }
}