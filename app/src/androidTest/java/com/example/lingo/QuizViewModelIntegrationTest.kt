package com.example.lingo

import com.example.lingo.domain.QuizViewModel
import com.example.lingo.network.QuizRepository
import com.example.lingo.network.RetrofitProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.UUID

@ExperimentalCoroutinesApi
class QuizViewModelIntegrationTest {

    private lateinit var viewModel: QuizViewModel
    private lateinit var repository: QuizRepository

    @Before
    fun setup() {
        repository = QuizRepository(RetrofitProvider.quizApiService)
        viewModel = QuizViewModel(repository)
    }

    @Test
    fun getScore_ExistentScore_RetrievesCorrectScore() = runTest {
        // Valid device id for test
        val deviceId = "3173997c-25d7-457d-8219-b510dd5c7522"
        // Test language for score
        val language = "{test language}"
        // Test quiz name for score
        val quizName = "test name"

        // Fetch the score back
        viewModel.getScore(deviceId, language, quizName)

        // Getting first entry in scoresMap
        viewModel.scoresMap.first { it.containsKey(quizName) }

        // Assigning score to val
        val score = viewModel.scoresMap.value[quizName]

        // Assert the correct score is returned
        Assert.assertEquals(3, score)
    }

    @Test
    fun getScore_NonExistentScore_NoScoreRetrieved() = runTest {
        // Nonexistent score parameters
        val nonExistentDeviceId = UUID.randomUUID().toString()
        val language = "nonexistent_language"
        val quizName = "nonexistent_quiz"

        // Giving user 3 correct answers
        viewModel.correctAnswers = 3

        // Try to get a nonexistent score
        viewModel.getScore(nonExistentDeviceId, language, quizName)

        // Wait to see if score is returned
        delay(500)

        val result = withTimeoutOrNull(500) {
            viewModel.scoresMap.first { it.containsKey(quizName) }
        }

        // Should be null if the score was never added
        Assert.assertNull(result)
    }
}