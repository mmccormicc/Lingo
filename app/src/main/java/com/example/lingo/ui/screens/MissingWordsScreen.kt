package com.example.lingo.ui.screens

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
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.lingo.R
import com.example.lingo.navigation.Routes
import com.example.lingo.domain.MissingWordsViewModel
import com.example.lingo.ui.components.ColorChangeButton

@Composable
fun MissingWordsScreen(navController : NavController, languageName: String) {

    val missingWordsViewModel: MissingWordsViewModel = viewModel()

    // Getting banner image depending on passed language name
    val imageResource = when (languageName.lowercase()) {
        "{spanish}" -> R.drawable.mexico_banner
        "{french}" -> R.drawable.france_banner
        "{german}" -> R.drawable.germany_banner
        "{italian}" -> R.drawable.italy_banner
        else -> R.drawable.germany_banner // Image to show if the name doesn't match
    }

    // Initializing picture match
    if (!missingWordsViewModel.initialized) {
        // Setting list of questions based on language
        missingWordsViewModel.setQuestions(languageName)
        // Getting random question to start
        missingWordsViewModel.nextQuestion()
        // Was initialized
        missingWordsViewModel.initialized = true
    }

    Column(
        Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // Painting banner
        AsyncImage(
            model = imageResource,
            contentDescription = languageName + "Banner",
            modifier = Modifier.fillMaxWidth().padding(bottom = 64.dp)
        )


        // Current question
        Text(
            text = missingWordsViewModel.currentQuestion.question,
            style = MaterialTheme.typography.displayMedium.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.padding(12.dp)
        )


        // Getting options available for question
        val answerOptions = missingWordsViewModel.currentQuestion.options

        // Looping through each option for a question, keeping track of index
        for ((index, option) in answerOptions.withIndex()) {

            // Determining if option is correct or not
            var isCorrect = false;
            if (index == missingWordsViewModel.currentQuestion.answer) {
                isCorrect = true;
            }

            // Custom option button that changes color depending on if answer is correct
            ColorChangeButton(
                missingWordsViewModel,
                isCorrect,
                option,
                missingWordsViewModel.currentQuestionIndex
            )

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