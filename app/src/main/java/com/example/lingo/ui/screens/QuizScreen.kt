package com.example.lingo.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lingo.R
import com.example.lingo.Routes
import com.example.lingo.data.Quiz
import com.example.lingo.domain.QuizViewModel

@Composable
fun QuizScreen(navController : NavController, languageName: String, quizNumber: Int, quizName: String) {

    val quizViewModel: QuizViewModel = viewModel()

    // Getting banner image depending on passed language name
    val imageResource = when (languageName.lowercase()) {
        "{spanish}" -> R.drawable.mexico_banner
        "{french}" -> R.drawable.france_banner
        "{german}" -> R.drawable.germany_banner
        "{italian}" -> R.drawable.italy_banner
        else -> R.drawable.germany_banner // Image to show if the name doesn't match
    }

    quizViewModel.setQuiz(quizNumber, languageName)

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // Painting banner
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = languageName + "Banner",
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
            contentScale = ContentScale.FillWidth
        )

        // Quiz name text
        Text(
            text = quizName,
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(top = 32.dp, bottom = 8.dp)
        )

        // Quiz progress text
        Text(
            text = "Question " + (quizViewModel.questionNumber + 1) + "/" + quizViewModel.numQuestions,
            style = TextStyle(
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        val quizQuestion = quizViewModel.getQuestion().question
        val answerOptions = quizViewModel.getQuestion().options

        // Current quiz question text
        Text(
            text = quizQuestion,
            style = TextStyle(
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
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

                        navController.navigate(Routes.quizResultScreen + "/$languageName/$quizName/$numQuestions/$correctAnswers")
                    }
                }
            ) {
                Text(
                    text = option,
                    style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(16.dp)
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
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.secondary),
                onClick = {
                    navController.navigate(Routes.homeScreen + "/$languageName")
                },
                modifier = Modifier.size(150.dp).padding(bottom = 100.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home",
                    modifier = Modifier.size(150.dp),
                )
            }
        }
    }
}