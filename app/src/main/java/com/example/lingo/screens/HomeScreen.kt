package com.example.lingo.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.navigation.NavController
import com.example.lingo.R
import com.example.lingo.Routes


@Composable
fun HomeScreen(navController : NavController, languageName: String) {

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
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        // Painting banner
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = languageName + "Banner",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

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
                        navController.navigate(Routes.flashcardSelectScreen + "/$languageName")
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
                        navController.navigate(Routes.quizSelectScreen  + "/$languageName")
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

        // Change language button
        OutlinedButton(
            modifier = Modifier.padding(16.dp),
            border = BorderStroke(width = 3.dp, color = Color.Black),
            onClick = {
                navController.navigate(Routes.languageSelectScreen)
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = "Italian Flag",
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = "Change Language",
                style = TextStyle(fontSize = 28.sp, color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(16.dp)
            )
        }

    }
}