package com.example.twit.navigation

import kotlinx.serialization.Serializable



@Serializable
object LoginScreen:RouteType

@Serializable
object MainScreen:RouteType

@Serializable
object SettingsScreen:RouteType

@Serializable
object SearchScreen:RouteType

@Serializable
data class InfoScreen(val id:Int):RouteType

@Serializable
object CalendarScreen:RouteType