package com.example.twit.navigation

sealed class Route (val route:String){
    data object LoginScreen:Route("login")
    data object MainScreen:Route("main")
    data object SettingsScreen:Route("settings")
    data object SearchScreen:Route("search")
}