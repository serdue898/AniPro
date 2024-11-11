package com.example.anipro.ui.screens.modifyAnime

import com.example.anipro.model.AnimeItem

open class ModifyStateUI {
    data class Success(
        var anime: AnimeItem
    ) : ModifyStateUI()

    object Error : ModifyStateUI()
    object Loading : ModifyStateUI()


}
