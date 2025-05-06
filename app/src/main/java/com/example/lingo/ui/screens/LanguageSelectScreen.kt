package com.example.lingo.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable

import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.lingo.R
import com.example.lingo.navigation.Routes


@Composable
fun LanguageSelectScreen(navController: NavController) {
    Column(
        Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Heading language selection text
        Text(
            text = "Choose a\nLanguage to\nLearn",
            style = MaterialTheme.typography.displayMedium
        )

        // Spacer between heading and buttons
        Spacer(modifier = Modifier.height(16.dp))

        // Spanish button
        OutlinedButton(
            onClick = {
                navController.navigate(Routes.homeScreen + "/{spanish}")
            },
            border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.primary)
        ) {
            AsyncImage(
                model = R.drawable.mexico_flag,
                contentDescription = "Mexico Flag",
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = "Spanish",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(16.dp)
            )
        }

        // Spacer between buttons
        Spacer(modifier = Modifier.height(16.dp))

        // French button
        OutlinedButton(
            onClick = {
                navController.navigate(Routes.homeScreen + "/{french}")
            },
            border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.primary)
        ) {
            AsyncImage(
                model = R.drawable.france_flag,
                contentDescription = "France Flag",
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = "French",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(16.dp)
            )
        }

        // Spacer between buttons
        Spacer(modifier = Modifier.height(16.dp))

        // German button
        OutlinedButton(
            onClick = {
                navController.navigate(Routes.homeScreen + "/{german}")
            },
            border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.primary)
        ){
            AsyncImage(
                model = R.drawable.germany_flag,
                contentDescription = "Germany Flag",
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = "German",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(16.dp)
            )
        }

        // Spacer between buttons
        Spacer(modifier = Modifier.height(16.dp))

        // Italian button
        OutlinedButton(
            onClick = {
                navController.navigate(Routes.homeScreen + "/{italian}")
            },
            border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.primary)
        ) {
            AsyncImage(
                model = R.drawable.italy_flag,
                contentDescription = "Italy Flag",
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = "Italian",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(16.dp)
            )
        }
        
    }
}