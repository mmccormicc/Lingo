package com.example.lingo.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable

import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun LanguageSelectScreen(navController: NavController) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        // Trying to debug theme not applying
        //Text(text = "Primary: ${MaterialTheme.colorScheme.primary}", color = MaterialTheme.colorScheme.primary)

        // Heading language selection text
        Text(
            text = "Choose a\nLanguage to\nLearn",
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

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
                style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold),
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
                style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold),
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
                style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold),
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
                style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}