package com.example.lingo.ui.startup

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.lingo.data.QuizScore
import com.example.lingo.network.QuizRepository
import com.example.lingo.network.RetrofitProvider
import com.example.lingo.utils.DeviceIdManager
import com.google.gson.Gson

@Composable
fun InitApp(
    content: @Composable () -> Unit
) {
    // Getting current app context
    val context = LocalContext.current

    // Launching coroutine
    LaunchedEffect(Unit) {
        // Getting or generating device ID on startup
        DeviceIdManager.getOrCreateDeviceId(context)
        // Try submitting cached scores to server
        retryCachedScores(context)
    }

    // Rest of app
    content()
}

suspend fun retryCachedScores(context: Context) {

    // Creating repository
    val repository = QuizRepository(RetrofitProvider.quizApiService)

    // Getting unsent scored scores shared prefs
    val prefs = context.getSharedPreferences("unsent_scores", Context.MODE_PRIVATE)
    // Creating gson object
    val gson = Gson()

    // Exiting function if pending is empty. Otherwise continue with cached scores.
    val cached = prefs.getString("pending", null) ?: return

    // Deserializing scores
    val list = gson.fromJson(cached, Array<QuizScore>::class.java).toMutableList()

    // Iterator for going through scores
    val iterator = list.iterator()

    // While there is still a score to send
    while (iterator.hasNext()) {
        // Getting score from list
        val score = iterator.next()

        try {
            // Sending score to server
            val response = repository.submitScore(score, context)
            // If successfully sent, removing from cached scores
            if (response.isSuccess) {
                iterator.remove()
            }
        // Catching generic exception
        } catch (e: Exception) {
            // Stopping score sending process as server is unreachable
            break
        }
    }

    // Inserting updated cached list of scores back into shared prefs
    prefs.edit().putString("pending", gson.toJson(list)).apply()
}