package com.example.twit.navigation

sealed class Route (val route:String){
    object LoginScreen:Route("login")
    object MainScreen:Route("main")
    object SettingsScreen:Route("settings")
    object SearchScreen:Route("search")
}