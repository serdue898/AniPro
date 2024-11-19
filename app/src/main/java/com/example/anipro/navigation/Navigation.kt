package com.example.anipro.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.anipro.ui.screens.calendar.Calendar
import com.example.anipro.ui.screens.info.Info
import com.example.anipro.ui.screens.login.Login
import com.example.anipro.ui.screens.modifyAnime.Modify
import com.example.anipro.ui.screens.search.Search
import com.example.anipro.ui.screens.settings.Settings

@Composable
fun Navigation() {
    val navController = rememberNavController()
    //TODO: Change startDestination to LoginScree
    //ModifyScreen(id = 54857)
    NavHost(navController = navController, startDestination =CalendarScreen) {
        composable<CalendarScreen> { Calendar(navController = navController) }
        composable<LoginScreen> { Login(navController = navController) }
        composable<SettingsScreen> { Settings(navController = navController) }
        composable<SearchScreen> { Search(navController = navController) }
        composable<InfoScreen> {
            val info = it.toRoute<InfoScreen>()
            Info(navController = navController, idAnime = info.id)
        }
        composable<ModifyScreen> {
            val info = it.toRoute<ModifyScreen>()
            Modify(navController = navController, idAnime = info.id)
        }
    }

}