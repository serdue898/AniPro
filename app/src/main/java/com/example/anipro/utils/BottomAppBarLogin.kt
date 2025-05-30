package com.example.anipro.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.anipro.navigation.CalendarScreen
import com.example.anipro.navigation.RouteType
import com.example.anipro.navigation.SearchScreen
import com.example.anipro.navigation.SettingsScreen

@Composable
fun BottomAppBarLogin(navController: NavController, ruta: RouteType) {
    NavigationBar {
        NavigationBarItem(
            selected = ruta == CalendarScreen,
            onClick = { navController.navigate(CalendarScreen) },
            icon = {
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = null)
            }, label = { Text(text = "Calendar") }
        )
        NavigationBarItem(
            selected = ruta == SearchScreen,
            onClick = { navController.navigate(SearchScreen) },
            icon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = null)
            }, label = { Text(text = "Search") }
        )
        NavigationBarItem(
            selected = ruta == SettingsScreen,
            onClick = { navController.navigate(SettingsScreen) },
            icon = {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = null)
            }, label = { Text(text = "Profile") }
        )
    }

}