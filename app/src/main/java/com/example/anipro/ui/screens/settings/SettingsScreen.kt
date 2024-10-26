package com.example.anipro.ui.screens.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.anipro.navigation.SettingsScreen
import com.example.anipro.ui.theme.TwitTheme
import com.example.anipro.utils.BottomAppBarLogin

@Composable
fun Settings(settingsViewModel: SettingsViewModel = viewModel(), navController: NavController) {
    TwitTheme {
        // A surface container using the 'background' color from the theme
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomAppBarLogin(navController = navController, ruta = SettingsScreen) },
            content = {
                SettingsMain(it)
            }
        )


    }
}

@Composable
fun SettingsMain(paddingValues: PaddingValues) {
    Text(text = "unos ajustes muy bonitos")

}