package com.example.twit.ui.screens.main

import com.example.twit.data.network.response.AnimeResponse
import com.example.twit.model.TwitData
import kotlinx.coroutines.flow.Flow
open class MainStateUI{
    data class Succes (
        var comments :Int = 0,
        var commentsCliked:Boolean=false,
        var replies:Int = 0,
        var repliesCliked:Boolean=false,
        var likesCliked:Boolean=false,
        var likes:Int = 0,
        var twits: Flow<List<TwitData>>,
        var animes:AnimeResponse
    ):MainStateUI()
    object Error : MainStateUI()
    object Loading : MainStateUI()
    object Loaded:MainStateUI()


}
