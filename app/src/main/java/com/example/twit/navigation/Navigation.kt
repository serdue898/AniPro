package com.example.twit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.twit.ui.screens.info.Info
import com.example.twit.ui.screens.login.Login
import com.example.twit.ui.screens.main.Main
import com.example.twit.ui.screens.search.Search
import com.example.twit.ui.screens.settings.Settings

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = LoginScreen) {
        composable<MainScreen> {Main(navController = navController)}
        composable<LoginScreen>{ Login(navController = navController) }
        composable<SettingsScreen>{ Settings(navController = navController) }
        composable<SearchScreen> { Search(navController = navController) }
        composable<InfoScreen> {
            val info = it.toRoute<InfoScreen>()
            Info(navController = navController, idAnime = info.id)
        }
    }

}