package com.example.lingo

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.example.lingo.data.QuizScore
import com.example.lingo.ui.startup.retryCachedScores
import com.google.gson.Gson
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RetryCachedScoresIntegrationTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }


    @Test
    fun retryCachedScores_ValidCachedScore_SubmitsAndClearsCache() = runTest {
        // Getting unsent scores shared prefs object
        val prefs = context.getSharedPreferences("unsent_scores", Context.MODE_PRIVATE)
        val gson = Gson()

        // Test score
        val testScore = QuizScore(
            deviceId = "test_id",
            language = "{test language}",
            quizName = "Noun Quiz",
            score = 5
        )

        // Adding score to cached pending scores
        val list = listOf(testScore)
        prefs.edit().putString("pending", gson.toJson(list)).commit()

        // Call the function
        retryCachedScores(context)

        // Getting scores remaining in cache
        val updatedCache = prefs.getString("pending", null)
        val remaining = updatedCache?.let {
            gson.fromJson(it, Array<QuizScore>::class.java).toList()
        } ?: emptyList()

        // Assert test score no longer in cache
        // This means it was sent successfully
        assert(!remaining.contains(testScore))
    }

    @Test
    fun retryCachedScores_EmptyUnsentScores_DoesNothingGracefully() = runTest {
        val prefs = context.getSharedPreferences("unsent_scores", Context.MODE_PRIVATE)

        // Clearing shared prefs object
        prefs.edit().clear().commit()

        // Ensure there are no pending scores
        assert(prefs.getString("pending", null) == null)

        // Run the function
        retryCachedScores(context)

        // Confirm no errors are thrown, there are no pending scores still
        val result = prefs.getString("pending", null)
        assert(result == null)
    }
}