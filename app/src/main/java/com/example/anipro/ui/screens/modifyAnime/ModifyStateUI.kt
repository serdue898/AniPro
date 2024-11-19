package com.example.anipro.ui.screens.modifyAnime

import com.example.anipro.model.AnimeItem

open class ModifyStateUI {
    data class Success(
        var anime: AnimeItem,
        var isNotification: Boolean
    ) : ModifyStateUI()

    object Error : ModifyStateUI()
    object Loading : ModifyStateUI()


}
