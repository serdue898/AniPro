package com.example.twit.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.twit.navigation.Route

@Composable
fun BottomAppBarLogin(navController: NavController, ruta: Route) {
    NavigationBar {
        NavigationBarItem(
            selected = ruta == Route.MainScreen,
            onClick = { navController.navigate(Route.MainScreen.route) },
            icon = {
                Icon(imageVector = Icons.Filled.Home, contentDescription = null)
            }
            , label = { Text(text = "Home")}
        )
        NavigationBarItem(
            selected = ruta == Route.SettingsScreen,
            onClick = { navController.navigate(Route.SettingsScreen.route) },
            icon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = null)
            }
            , label = { Text(text = "Search")}
        )
        NavigationBarItem(
            selected = ruta == Route.SettingsScreen,
            onClick = { navController.navigate(Route.SettingsScreen.route) },
            icon = {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = null)
            }
            , label = { Text(text = "Profile")}
        )
    }

}