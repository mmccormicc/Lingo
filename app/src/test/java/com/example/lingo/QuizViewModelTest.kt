package com.example.lingo

import com.example.lingo.data.Quiz
import com.example.lingo.data.QuizQuestion
import com.example.lingo.domain.QuizViewModel
import com.example.lingo.network.QuizRepository
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

@ExperimentalCoroutinesApi
class QuizViewModelTest {

    private lateinit var viewModel: QuizViewModel
    private lateinit var repository: QuizRepository

    @Before
    fun setup() {
        // Repository is never used in these tests
        repository = mockk(relaxed = true)
        // Creating test view model
        viewModel = QuizViewModel(repository)

        // Test quiz
        val testQuiz = Quiz(
            listOf(
                QuizQuestion("Q1", 1, listOf("A", "B", "C", "D")),
                QuizQuestion("Q2", 2, listOf("A", "B", "C", "D"))
            )
        )

        viewModel.quiz = testQuiz
        viewModel.numQuestions = testQuiz.questions.size
        viewModel.questionNumber = 0
        viewModel.correctAnswers = 0
    }

    @Test
    fun nextQuestion_CorrectAnswer_NotLastQuestion_AdvancesAndIncreasesScore() {
        // Testing correct answer for question 1
        val quizOver = viewModel.nextQuestion(1)
        assertEquals(1, viewModel.questionNumber)
        assertEquals(1, viewModel.correctAnswers)
        assertFalse(quizOver)
    }

    @Test
    fun nextQuestion_IncorrectAnswer_NotLastQuestion_AdvancesButNoIncreasedScore() {
        // Testing incorrect answer for question 1
        val quizOver = viewModel.nextQuestion(0)
        assertEquals(1, viewModel.questionNumber)
        assertEquals(0, viewModel.correctAnswers)
        assertFalse(quizOver)
    }

    @Test
    fun nextQuestion_CorrectAnswer_LastQuestion_ScoreIncreasedAndEndsQuiz() {
        // First question
        viewModel.nextQuestion(1)
        // Last question
        val isQuizOver = viewModel.nextQuestion(2)

        // Both questions correct
        assertEquals(2, viewModel.correctAnswers)
        assertTrue(isQuizOver)
    }

    @Test
    fun nextQuestion_IncorrectAnswer_LastQuestion_ScoreNotIncreasedAndEndsQuiz() {
        // First question
        viewModel.nextQuestion(0)
        // Last question
        val isQuizOver = viewModel.nextQuestion(0)

        // Both questions incorrect
        assertEquals(0, viewModel.correctAnswers)
        assertTrue(isQuizOver)
    }

}