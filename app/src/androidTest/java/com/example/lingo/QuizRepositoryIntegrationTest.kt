package com.example.lingo

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.example.lingo.data.QuizScore
import com.example.lingo.network.QuizApiService
import com.example.lingo.network.QuizRepository
import com.example.lingo.network.RetrofitProvider
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class QuizRepositoryIntegrationTest {

    private lateinit var repository: QuizRepository
    private lateinit var context: Context
    private lateinit var failingRepository: QuizRepository

    @Before
    fun setup() {
        repository = QuizRepository(RetrofitProvider.quizApiService)
        context = InstrumentationRegistry.getInstrumentation().targetContext
        failingRepository = QuizRepository(createRetrofitWithInvalidUrl())
    }

    // Create retrofit instance with bad url
    private fun createRetrofitWithInvalidUrl(): QuizApiService {
        return Retrofit.Builder()
            .baseUrl("http://localhost:9999/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuizApiService::class.java)
    }

    @Test
    fun submitScore_ExistentScore_IsSuccessful() = runTest {

        val quizScore = QuizScore(
            deviceId = "test_id",
            language = "{test language}",
            quizName = "Noun Quiz",
            score = 10
        )

        val result = repository.submitScore(quizScore, context)

        assert(result.isSuccess)
    }

    @Test
    fun submitScore_NetworkFailure_ScoreIsCached() = runTest {

        val quizScore = QuizScore(
            deviceId = "test_id",
            language = "{test language}",
            quizName = "Noun Quiz",
            score = 10
        )

        // Clearing unsent scores for testing
        val prefs = context.getSharedPreferences("unsent_scores", Context.MODE_PRIVATE)
        prefs.edit().clear().commit()

        // Submitting score with failed repository
        val result = failingRepository.submitScore(quizScore, context)

        // Asserting score not submitted
        assert(result.isFailure)

        // Checking that score was cached
        val cachedJson = prefs.getString("pending", null)
        assert(cachedJson != null)
        val cachedScores = Gson().fromJson(cachedJson, Array<QuizScore>::class.java).toList()
        assert(cachedScores.contains(quizScore))
    }

    @Test
    fun submitDeviceId_ValidDeviceId_IsSuccessful() = runTest {
        val validDeviceId = "test_id"

        // Should submit successfully
        repository.submitDeviceId(validDeviceId)

        // Asserting that no exceptions were encountered
        assert(true)
    }

    @Test
    fun submitDeviceId_NetworkFailure_HandlesException() = runTest {
        try {
            failingRepository.submitDeviceId("test_id")
            // Network error exception handled by repository
            assert(true)
        } catch (e: Exception) {
            // Test fails if exception not handled
            assert(false) { "submitDeviceId threw an exception: $e" }
        }
    }

}