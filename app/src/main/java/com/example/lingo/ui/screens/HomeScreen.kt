package com.example.lingo.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.lingo.R
import com.example.lingo.navigation.Routes


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
        Modifier
            .fillMaxSize()
            // This prevents app from overlapping system bar with time and battery life symbols
            .windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // Painting banner
        AsyncImage(
            model = imageResource,
            contentDescription = languageName + "Banner",
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            Modifier.fillMaxSize(),
            // Centering blocks horizontally and spacing them evenly on screen
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly

        ) {

            // Practice column
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Practice text
                Text(
                    text = "Practice",
                    style = MaterialTheme.typography.displayMedium

                )

                // Practice buttons surrounded by border
                Column(
                    Modifier
                        .fillMaxWidth()
                        // Surrounding practice buttons with gray background
                        .background(MaterialTheme.colorScheme.outlineVariant),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Flashcards button
                    Button(
                        modifier = Modifier.padding(8.dp),
                        onClick = {
                            navController.navigate(Routes.flashcardSelectScreen + "/$languageName")
                        }
                    ) {
                        Text(
                            text = "Flashcards",
                            style = MaterialTheme.typography.displaySmall.copy(
                                color = MaterialTheme.colorScheme.background
                            ),
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    // Missing words button
                    Button(
                        modifier = Modifier.padding(8.dp),
                        onClick = {
                            navController.navigate(Routes.missingWordsScreen + "/$languageName")
                        }
                    ) {
                        Text(
                            text = "Missing Words",
                            style = MaterialTheme.typography.displaySmall.copy(
                                color = MaterialTheme.colorScheme.background
                            ),
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    // Picture match button
                    Button(
                        modifier = Modifier.padding(8.dp),
                        onClick = {
                            navController.navigate(Routes.pictureMatchScreen + "/$languageName")
                        }
                    ) {
                        Text(
                            text = "Picture Match",
                            style = MaterialTheme.typography.displaySmall.copy(
                                color = MaterialTheme.colorScheme.background
                            ),
                            modifier = Modifier.padding(8.dp)
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
                    style = MaterialTheme.typography.displayMedium,
                )

                // Test buttons surrounded by border
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.outlineVariant),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Quizzes button
                    Button(
                        modifier = Modifier.padding(8.dp),
                        onClick = {
                            navController.navigate(Routes.quizSelectScreen + "/$languageName")
                        }
                    ) {
                        Text(
                            text = "Quizzes",
                            style = MaterialTheme.typography.displaySmall.copy(
                                color = MaterialTheme.colorScheme.background
                            ),
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                }
            }

            // Change language button
            OutlinedButton(
                modifier = Modifier.padding(8.dp),
                border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.onBackground),
                onClick = {
                    navController.navigate(Routes.languageSelectScreen)
                }
            ) {
                AsyncImage(
                    model = R.drawable.back_arrow,
                    contentDescription = "Back Arrow",
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = "Change Language",
                    style = MaterialTheme.typography.displaySmall.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier.padding(8.dp)
                )
            }

        }
    }
}