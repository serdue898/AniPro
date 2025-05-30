package com.example.anipro.ui.screens.settings




open class SettingsStateUI {
    data class Success(
        var theme: String,
        var color: String,
        var grid: String
    ) : SettingsStateUI()

    object Error : SettingsStateUI()
    object Loading : SettingsStateUI()


}