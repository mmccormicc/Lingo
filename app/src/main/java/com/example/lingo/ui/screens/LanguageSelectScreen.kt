package com.example.lingo.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable

import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lingo.R
import com.example.lingo.Routes
import com.example.lingo.ui.theme.LocalScreenSize


@Composable
fun LanguageSelectScreen(navController: NavController) {
    Column(
        Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        // Heading language selection text
        Text(
            text = "Choose a\nLanguage to\nLearn",
            style = MaterialTheme.typography.displayMedium
        )

        // Spanish button
        OutlinedButton(
            onClick = {
                navController.navigate(Routes.homeScreen + "/{spanish}")
            },
            border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.primary)
        ) {
            Image(
                painter = painterResource(id = R.drawable.mexico_flag),
                contentDescription = "Mexican Flag",
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = "Spanish",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(16.dp)
            )
        }

        // French button
        OutlinedButton(
            onClick = {
                navController.navigate(Routes.homeScreen + "/{french}")
            },
            border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.primary)
        ) {
            Image(
                painter = painterResource(id = R.drawable.france_flag),
                contentDescription = "French Flag",
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = "French",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(16.dp)
            )
        }

        // German button
        OutlinedButton(
            onClick = {
                navController.navigate(Routes.homeScreen + "/{german}")
            },
            border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.primary)
        ){
            Image(
                painter = painterResource(id = R.drawable.germany_flag),
                contentDescription = "German Flag",
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = "German",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(16.dp)
            )
        }

        // Italian button
        OutlinedButton(
            onClick = {
                navController.navigate(Routes.homeScreen + "/{italian}")
            },
            border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.primary)
        ) {
            Image(
                painter = painterResource(id = R.drawable.italy_flag),
                contentDescription = "Italian Flag",
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = "Italian",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(16.dp)
            )
        }

        val configuration = LocalConfiguration.current
        // This adds a box to the bottom of the screen for some blank space based on screen height
        Box(
            modifier = Modifier
                .heightIn(min = (configuration.screenHeightDp/8).dp) // Limit width
        )
    }
}