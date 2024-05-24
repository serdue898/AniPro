package com.example.twit.ui.screens.info

import com.example.twit.model.AnimeItem

open class InfoStateUI {

        data class Success(
            var anime:AnimeItem
        ):InfoStateUI()
        object Error : InfoStateUI()
        object Loading : InfoStateUI()
        data class ShowPopup(
            var anime:AnimeItem
        ):InfoStateUI()
}