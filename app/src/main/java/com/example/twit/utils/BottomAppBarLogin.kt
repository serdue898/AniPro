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
import com.example.twit.navigation.MainScreen
import com.example.twit.navigation.RouteType
import com.example.twit.navigation.SearchScreen
import com.example.twit.navigation.SettingsScreen

@Composable
fun BottomAppBarLogin(navController: NavController, ruta : RouteType) {
    NavigationBar {
        NavigationBarItem(
            selected = ruta == MainScreen,
            onClick = { navController.navigate(MainScreen) },
            icon = {
                Icon(imageVector = Icons.Filled.Home, contentDescription = null)
            }
            , label = { Text(text = "Home")}
        )
        NavigationBarItem(
            selected = ruta == SearchScreen,
            onClick = { navController.navigate(SearchScreen) },
            icon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = null)
            }
            , label = { Text(text = "Search")}
        )
        NavigationBarItem(
            selected = ruta == SettingsScreen,
            onClick = { navController.navigate(SettingsScreen) },
            icon = {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = null)
            }
            , label = { Text(text = "Profile")}
        )
    }

}