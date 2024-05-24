package com.example.anipro.ui.screens.main

import com.example.anipro.model.AnimeItem

open class MainStateUI {
    data class Success(
        var animes: List<AnimeItem>
    ) : MainStateUI()

    object Error : MainStateUI()
    object Loading : MainStateUI()


}
