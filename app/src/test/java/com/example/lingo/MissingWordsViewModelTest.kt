package com.example.lingo

import com.example.lingo.data.QuizQuestion
import com.example.lingo.domain.MissingWordsViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MissingWordsViewModelTest {

    private lateinit var viewModel: MissingWordsViewModel

    @Before
    fun setUp() {
        // Initialize the ViewModel before each test
        viewModel = MissingWordsViewModel()
        // Set some mock questions for testing
        viewModel.setQuestions("{spanish}")
    }

    @Test
    fun setQuestions_SetToSpanishQuestions_HasCorrectNumberOfQuestions() {
        // Assert that the unseenQuestions list has the same size as the spanishQuestions list
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
        assertNotEquals(QuizQuestion("Error", 0, listOf("Error", "Error")), viewModel.currentQuestion)
    }

    @Test
    fun nextQuestion_OneQuestionLeft_ResetsQuestionsThenGoesNext() {
        // Looping through all but one of the questions
        for (i in 1 until viewModel.spanishQuestions.size) {
            viewModel.nextQuestion()
        }

        // Asserting there is 1 question left
        assertEquals(1, viewModel.unseenQuestions.size)

        viewModel.nextQuestion()

        // Assert that the last question was transferred to seen questions
        assertEquals(1, viewModel.seenQuestions.size)

        // Assert that unseen questions have all but one question
        assertEquals(viewModel.spanishQuestions.size - 1, viewModel.unseenQuestions.size)

        // Assert that the current question has been updated
        assertNotEquals(QuizQuestion("Error", 0, listOf("Error", "Error")), viewModel.currentQuestion)
    }

}