package com.example.lingo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lingo.Routes
import com.example.lingo.data.Quiz
import com.example.lingo.datahelper.QuizHelper

@Composable
fun QuizScreen(navController : NavController, languageName: String, quizNumber: Int) {

    // Getting banner image depending on passed language name
    val imageResource = when (languageName.lowercase()) {
        "{spanish}" -> com.example.lingo.R.drawable.mexico_banner
        "{french}" -> com.example.lingo.R.drawable.france_banner
        "{german}" -> com.example.lingo.R.drawable.germany_banner
        "{italian}" -> com.example.lingo.R.drawable.italy_banner
        else -> com.example.lingo.R.drawable.germany_banner // Image to show if the name doesn't match
    }

    // Getting list of quizzes depending on language name
    val quizzes = when (languageName.lowercase()) {
        "{spanish}" -> QuizHelper.spanishQuizzes
        "{french}" -> QuizHelper.spanishQuizzes
        "{german}" -> QuizHelper.spanishQuizzes
        "{italian}" -> QuizHelper.spanishQuizzes
        else -> com.example.lingo.R.drawable.germany_banner // Image to show if the name doesn't match
    } as List<Quiz>

    val Quiz = quizzes[quizNumber]

    val quizQuestion by remember { mutableStateOf(Quiz.questions[0].question)}
    val answerOptions by remember { mutableStateOf(Quiz.questions[0].options)}

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

        // Current quiz question
        Text(
            text = quizQuestion,
            style = TextStyle(
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(64.dp)
        )

        // Quiz answer options
        for (option in answerOptions ) {
            Button(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    navController.navigate(Routes.flashcardSelectScreen + "/$languageName")
                }
            ) {
                Text(
                    text = option,
                    style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}