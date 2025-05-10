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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.lingo.R
import com.example.lingo.navigation.Routes
import com.example.lingo.domain.PictureMatchViewModel
import com.example.lingo.ui.components.ColorChangeButton

@Composable
fun PictureMatchScreen(navController : NavController, languageName: String) {

    val pictureMatchViewModel: PictureMatchViewModel = viewModel()

    // Getting banner image depending on passed language name
    val imageResource = when (languageName.lowercase()) {
        "{spanish}" -> R.drawable.mexico_banner
        "{french}" -> R.drawable.france_banner
        "{german}" -> R.drawable.germany_banner
        "{italian}" -> R.drawable.italy_banner
        else -> R.drawable.germany_banner // Image to show if the name doesn't match
    }

    // Initializing picture match
    if (!pictureMatchViewModel.initialized) {
        // Setting list of questions based on language
        pictureMatchViewModel.setQuestions(languageName)
        // Getting random question to start
        pictureMatchViewModel.nextQuestion()
        // Was initialized
        pictureMatchViewModel.initialized = true
    }

    // Selecting image size depending on screen height
    val configuration = LocalConfiguration.current
    val imageSize = when {
        configuration.screenHeightDp < 800 -> 200.dp   // Small screens
        else -> 300.dp                  // Medium / Large screens
    }

    Column(
        Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // Painting banner
        AsyncImage(
            model = imageResource,
            contentDescription = languageName + "Banner",
            modifier = Modifier.fillMaxWidth()
        )

        // Painting current question image
        AsyncImage(
            model = pictureMatchViewModel.currentQuestion.pictureID,
            contentDescription = stringResource(pictureMatchViewModel.currentQuestion.pictureID),
            modifier = Modifier.size(imageSize)
        )

        // Getting options available for question
        val answerOptions = pictureMatchViewModel.currentQuestion.options

        // Looping through each option for a question, keeping track of index
        for ((index, option) in answerOptions.withIndex()) {

            // Determining if option is correct or not
            var isCorrect = false;
            if (index == pictureMatchViewModel.currentQuestion.answer) {
                isCorrect = true;
            }

            // Custom option button that changes color depending on if answer is correct
            ColorChangeButton(pictureMatchViewModel, isCorrect, option, pictureMatchViewModel.currentQuestionIndex)

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