package com.example.lingo.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.lingo.R
import com.example.lingo.navigation.Routes
import com.example.lingo.domain.QuizViewModel
import com.example.lingo.domain.QuizViewModelFactory
import com.example.lingo.network.QuizRepository
import com.example.lingo.network.RetrofitProvider
import com.example.lingo.utils.DeviceIdManager

@Composable
fun QuizScreen(navController : NavController, languageName: String, quizNumber: Int, quizName: String) {


    val repository = remember { QuizRepository(RetrofitProvider.quizApiService) }
    val quizViewModel: QuizViewModel = viewModel(factory = QuizViewModelFactory(repository))

    val context = LocalContext.current

    // Getting banner image depending on passed language name
    val imageResource = when (languageName.lowercase()) {
        "{spanish}" -> R.drawable.mexico_banner
        "{french}" -> R.drawable.france_banner
        "{german}" -> R.drawable.germany_banner
        "{italian}" -> R.drawable.italy_banner
        else -> R.drawable.germany_banner // Image to show if the name doesn't match
    }

    // Setting quiz based on language and quiz number selected
    quizViewModel.setQuiz(quizNumber, languageName)

    Column(
        Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // Painting banner
        AsyncImage(
            model = imageResource,
            contentDescription = languageName + "Banner",
            modifier = Modifier.fillMaxWidth()
        )

        // Quiz name text
        Text(
            text = quizName,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = 32.dp, bottom = 8.dp)
        )

        // Quiz progress text
        Text(
            text = "Question " + (quizViewModel.questionNumber + 1) + "/" + quizViewModel.numQuestions,
            style = MaterialTheme.typography.displaySmall.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        val quizQuestion = quizViewModel.getQuestion().question
        val answerOptions = quizViewModel.getQuestion().options

        // Current quiz question text
        Text(
            text = quizQuestion,
            style = MaterialTheme.typography.displaySmall.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
           modifier = Modifier.padding(bottom = 16.dp)
        )

        // Looping through each option for a question, keeping track of index
        for ((index, option) in answerOptions.withIndex()) {
            // Creating option button
            Button(
                modifier = Modifier.padding(4.dp).fillMaxWidth(),
                onClick = {
                    println(quizViewModel.numQuestions)
                    // Getting next quiz question, and if end of quiz is reached change to result screen
                    if(quizViewModel.nextQuestion(index)) {
                        val numQuestions = quizViewModel.numQuestions
                        val correctAnswers = quizViewModel.correctAnswers

                        // Getting device id
                        val deviceId = DeviceIdManager.getCachedDeviceId()
                        // If device id was found
                        if (deviceId != null) {
                            // Submitting score to remote server
                            quizViewModel.submitScore(
                                deviceId,
                                languageName,
                                quizName,
                                context
                            )
                        }

                        // Navigating to result screen
                        navController.navigate(Routes.quizResultScreen + "/$languageName/$quizName/$numQuestions/$correctAnswers")
                    }
                }
            ) {
                Text(
                    text = option,
                    style = MaterialTheme.typography.displaySmall.copy(
                        color = MaterialTheme.colorScheme.background
                    ),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        // Home button column to align to bottom
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            // Home button
            FilledIconButton(
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.onSurfaceVariant),
                onClick = {
                    navController.navigate(Routes.homeScreen + "/$languageName")
                },
                modifier = Modifier.size(80.dp).padding(bottom = 20.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home",
                    modifier = Modifier.size(80.dp),
                    tint = Color.White
                )
            }
        }
    }
}