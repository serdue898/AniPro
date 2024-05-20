package com.example.twit.ui.screens.main

import com.example.twit.model.AnimeItem
import com.example.twit.model.TwitData
import kotlinx.coroutines.flow.Flow
open class MainStateUI{
    data class Success(
        var comments:Int = 0,
        var commentsClicked:Boolean=false,
        var replies:Int = 0,
        var repliesClicked:Boolean=false,
        var likesClicked:Boolean=false,
        var likes:Int = 0,
        var twits: Flow<List<TwitData>>,
        var animes: List<AnimeItem>
    ):MainStateUI()
    object Error : MainStateUI()
    object Loading : MainStateUI()


}
