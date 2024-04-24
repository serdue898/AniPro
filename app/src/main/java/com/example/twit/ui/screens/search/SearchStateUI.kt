package com.example.twit.ui.screens.search

import com.example.twit.model.AnimeItem
import com.example.twit.model.TwitData
import kotlinx.coroutines.flow.Flow
open class SearchStateUI{
    data class Succes(
        var comments:Int = 0,
        var commentsCliked:Boolean=false,
        var replies:Int = 0,
        var repliesCliked:Boolean=false,
        var likesCliked:Boolean=false,
        var likes:Int = 0,
        var twits: Flow<List<TwitData>>,
        var animes: List<AnimeItem>,
        var search : String = "",
        val isSearching:Boolean = false,
        var searchResult:List<AnimeItem> = emptyList()
    ):SearchStateUI()
    object Error : SearchStateUI()
    object Loading : SearchStateUI()
    object Loaded:SearchStateUI()


}
