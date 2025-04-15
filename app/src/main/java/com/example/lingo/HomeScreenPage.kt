package com.example.lingo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lingo.ui.theme.customFont


@Composable
fun HomeScreenPage(navController: NavController) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Screen A",
            style = TextStyle(fontSize = 24.sp, fontFamily = customFont, fontWeight = FontWeight.Normal)
        )
        Button(onClick = {
            navController.navigate(Routes.screenB+"/john")
        }) {
            Text(text = "Goto Screen B")
        }

    }
}