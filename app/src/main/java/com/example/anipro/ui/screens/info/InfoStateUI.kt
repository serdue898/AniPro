package com.example.anipro.ui.screens.info

import com.example.anipro.model.AnimeItem

open class InfoStateUI {

    data class Success(
        var anime: AnimeItem
    ) : InfoStateUI()

    object Error : InfoStateUI()
    object Loading : InfoStateUI()
    data class ShowPopup(
        var anime: AnimeItem,
        var episodes: Int
    ) : InfoStateUI()
}