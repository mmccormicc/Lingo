package com.example.lingo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun HomeScreen(navController : NavController, name: String) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        // Practice column
        Column(
            //Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Practice text
            Text(
                text = "Practice",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )

            )

            // Practice buttons surrounded by border
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Flashcards button
                Button(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        navController.navigate(Routes.languageSelectScreen)
                    }
                ) {
                    Text(
                        text = "Flashcards",
                        style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(16.dp)
                    )
                }

                // Missing words button
                Button(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        navController.navigate(Routes.languageSelectScreen)
                    }
                ) {
                    Text(
                        text = "Missing Words",
                        style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(16.dp)
                    )
                }

                // Picture match button
                Button(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        navController.navigate(Routes.languageSelectScreen)
                    }
                ) {
                    Text(
                        text = "Picture Match",
                        style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        // Test column
        Column(
            //Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Test text
            Text(
                text = "Test",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            // Test buttons surrounded by border
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // quizzes button
                Button(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        navController.navigate(Routes.languageSelectScreen)
                    }
                ) {
                    Text(
                        text = "Quizzes",
                        style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(16.dp)
                    )
                }

            }
        }

    }
}