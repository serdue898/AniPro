package com.example.anipro.ui.screens.info

import com.example.anipro.model.AnimeItem
import java.sql.Date
import java.time.LocalDate

open class InfoStateUI {

    data class Success(
        var anime: AnimeItem,
        var isAnimeCreate: Boolean
    ) : InfoStateUI()

    object Error : InfoStateUI()
    object Loading : InfoStateUI()
}