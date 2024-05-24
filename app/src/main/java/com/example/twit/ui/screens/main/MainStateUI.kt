package com.example.twit.ui.screens.main

import com.example.twit.model.AnimeItem
import com.example.twit.model.TwitData
import kotlinx.coroutines.flow.Flow
open class MainStateUI{
    data class Success(
        var animes: List<AnimeItem>
    ):MainStateUI()
    object Error : MainStateUI()
    object Loading : MainStateUI()


}
