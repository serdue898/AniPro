package com.example.twit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.twit.ui.screens.login.Login
import com.example.twit.ui.screens.main.Main
import com.example.twit.ui.screens.search.Search
import com.example.twit.ui.screens.settings.Settings

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Route.LoginScreen.route) {
        composable(Route.MainScreen.route) {Main(navController = navController)}
        composable(Route.LoginScreen.route) { Login(navController = navController) }
        composable(Route.SettingsScreen.route) { Settings(navController = navController) }
        composable(Route.SearchScreen.route) { Search(navController = navController) }
    }

}