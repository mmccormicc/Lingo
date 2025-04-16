package com.example.lingo

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lingo.screens.FlashCardSelectScreen
import com.example.lingo.screens.HomeScreen
import com.example.lingo.screens.LanguageSelectScreen
import com.example.lingo.screens.QuizSelectScreen

@Composable

fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.languageSelectScreen/*Routes.homeScreen+"/{french}"*/, builder =  {
        composable(Routes.languageSelectScreen) {
            LanguageSelectScreen(navController)
        }
        composable(Routes.homeScreen+"/{languagename}") {
            var languageName = it.arguments?.getString("languagename")
            HomeScreen(navController, languageName ?: "No language")
        }
        composable(Routes.flashcardSelectScreen+"/{languagename}") {
            var languageName = it.arguments?.getString("languagename")
            FlashCardSelectScreen(navController, languageName ?: "No language")
        }
        composable(Routes.quizSelectScreen+"/{languagename}") {
            var languageName = it.arguments?.getString("languagename")
            QuizSelectScreen(navController, languageName ?: "No language")
        }
    })
}