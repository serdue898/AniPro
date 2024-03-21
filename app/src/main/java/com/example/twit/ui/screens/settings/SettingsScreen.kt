package com.example.twit.ui.screens.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.twit.navigation.Route
import com.example.twit.ui.theme.TwitTheme
import com.example.twit.utils.BottomAppBarLogin

@Composable
fun settings(settingsViewModel: SettingsViewModel = viewModel(),navController: NavController){
    TwitTheme {
        // A surface container using the 'background' color from the theme
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomAppBarLogin(navController = navController, ruta = Route.SettingsScreen) },
            content = {
                settingsmain(it)
            }
        )


    }
}@Composable
fun settingsmain(paddingValues: PaddingValues) {
    Text(text = "unos ajustes muy bonitos")

}