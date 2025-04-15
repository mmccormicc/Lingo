package com.example.lingo

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable

fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.languageSelectScreen, builder =  {
        composable(Routes.languageSelectScreen) {
            LanguageSelectScreen(navController)
        }
        composable(Routes.homeScreen+"/{name}") {
            var name = it.arguments?.getString("name")
            HomeScreen(navController, name?:"No name")
        }
    })
}