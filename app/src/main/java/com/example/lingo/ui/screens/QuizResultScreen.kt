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
import androidx.compose.ui.graphics.Color
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
fun QuizResultScreen(navController : NavController, languageName: String, quizName: String, numQuestions : Int, correctQuestions : Int) {

    // Getting banner image depending on passed language name
    val imageResource = when (languageName.lowercase()) {
        "{spanish}" -> R.drawable.mexico_banner
        "{french}" -> R.drawable.france_banner
        "{german}" -> R.drawable.germany_banner
        "{italian}" -> R.drawable.italy_banner
        else -> R.drawable.germany_banner // Image to show if the name doesn't match
    }


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
            modifier = Modifier.padding(top = 32.dp, bottom = 32.dp)
        )

        // Quiz Result
        Text(
            text = "Score " + correctQuestions + "/" + numQuestions,
            style = TextStyle(
                color = Color.Green,
                textAlign = TextAlign.Center,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            ),
        )

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