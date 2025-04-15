package com.example.lingo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController


@Composable
fun ScreenB(navController : NavController, name: String) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Screen B and name is $name")

        Button(onClick = {
            navController.navigate(Routes.homeScreen)
        }) {
            Text(text = "Goto Screen A")
        }

    }
}