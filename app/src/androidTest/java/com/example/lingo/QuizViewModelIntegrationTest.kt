package com.example.lingo

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.lingo.domain.QuizViewModel
import com.example.lingo.network.QuizRepository
import com.example.lingo.network.RetrofitProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
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
    fun SubmitAndGetScore_NewDeviceAndValidScore_RetrievesCorrectScore() = runTest {
        // Test context
        val context = ApplicationProvider.getApplicationContext<Context>()
        // Random device id for test
        val deviceId = UUID.randomUUID().toString()
        val language = "{test language}"
        val quizName = "test name"

        // Giving user 3 correct answers
        viewModel.correctAnswers = 3

        // Submit score to the real backend
        viewModel.submitScore(deviceId, language, quizName, context)

        // Wait for server processing
        delay(5000)

        // Fetch the score back
        viewModel.getScore(deviceId, language, quizName)

        // Wait for the flow to update
        viewModel.scoresMap.first { it.containsKey(quizName) }

        // Assigning score to val
        val score = viewModel.scoresMap.value[quizName]

        // Assert the correct score is returned
        Assert.assertEquals(3, score)
    }
}