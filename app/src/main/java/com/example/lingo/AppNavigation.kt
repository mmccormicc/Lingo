package com.example.lingo

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable

fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.homeScreen, builder =  {
        composable(Routes.homeScreen) {
            HomeScreenPage(navController)
        }
        composable(Routes.screenB+"/{name}") {
            var name = it.arguments?.getString("name")
            ScreenB(navController, name?:"No name")
        }
    })
}