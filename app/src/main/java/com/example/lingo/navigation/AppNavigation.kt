package com.example.lingo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lingo.Routes
import com.example.lingo.ui.screens.FlashCardSelectScreen
import com.example.lingo.ui.screens.HomeScreen
import com.example.lingo.ui.screens.LanguageSelectScreen
import com.example.lingo.ui.screens.QuizResultScreen
import com.example.lingo.ui.screens.QuizScreen
import com.example.lingo.ui.screens.QuizSelectScreen

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
        composable(
            Routes.quizScreen+"/{languagename}/{quiznumber}/{quizname}",
                arguments = listOf(
                navArgument("quiznumber") { type = NavType.IntType }, // Define quiznumber as IntType
                navArgument("languagename") { type = NavType.StringType },
                navArgument("quizname") { type = NavType.StringType }
        )) {
            var languageName = it.arguments?.getString("languagename")
            var quizNumber = it.arguments?.getInt("quiznumber")
            var quizName = it.arguments?.getString("quizname")
            QuizScreen(navController, languageName ?: "No language", quizNumber ?: -1, quizName ?: "No Quiz")
        }
        composable(
            Routes.quizResultScreen+"/{languagename}/{quizname}/{numquestions}/{correctquestions}",
            arguments = listOf(
                navArgument("languagename") { type = NavType.StringType },
                navArgument("quizname") { type = NavType.StringType },
                navArgument("numquestions") { type = NavType.IntType },
                navArgument("correctquestions") { type = NavType.IntType }
            )) {
            var languageName = it.arguments?.getString("languagename")
            var quizName = it.arguments?.getString("quizname")
            var numQuestions = it.arguments?.getInt("numquestions")
            var correctQuestions = it.arguments?.getInt("correctquestions")
            QuizResultScreen(navController, languageName ?: "No language", quizName ?: "No name", numQuestions ?: -1, correctQuestions ?: -1)
        }
    })
}