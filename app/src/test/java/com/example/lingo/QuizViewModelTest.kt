package com.example.lingo

import com.example.lingo.domain.QuizViewModel
import com.example.lingo.network.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before

import com.example.lingo.data.QuizScore
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test
import retrofit2.Response

import org.junit.Assert.assertEquals

@ExperimentalCoroutinesApi
class QuizViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: QuizRepository
    private lateinit var viewModel: QuizViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = QuizViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun GetScore_UpdatesScoreMap() = runTest {
        // Test QuizScore info
        val deviceId = "device123"
        val language = "french"
        val quizName = "vocab_quiz"

        // Score that should be returned
        val expectedScore = 7

        val quizScore = QuizScore(deviceId, language, quizName, expectedScore)

        // Creating mock response using quiz score
        val mockResponse = Response.success(quizScore)

        // Mocking repository.getScore with mockResponse returned
        coEvery { repository.getScore(deviceId, language, quizName) } returns mockResponse

        // Calling viewModel.getScore which runs repository.getScore
        viewModel.getScore(deviceId, language, quizName)
        // Let coroutine complete
        advanceUntilIdle()

        // Getting scores map
        val scoresMap = viewModel.scoresMap.value
        // Asserting that correct score was put in map
        assertEquals(expectedScore, scoresMap[quizName])
    }
}