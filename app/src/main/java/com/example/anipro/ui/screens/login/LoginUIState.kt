package com.example.anipro.ui.screens.login

data class LoginUIState(
    var email: String = "",
    var password: String = "",
    var showPassword: Boolean = false,
    var LogInEnable: Boolean = true

)