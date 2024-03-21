package com.example.twit.ui.screens.main

import com.example.twit.model.TwitData
import kotlinx.coroutines.flow.Flow

data class MainStateUI (
    var comments :Int = 0,
    var commentsCliked:Boolean=false,
    var replies:Int = 0,
    var repliesCliked:Boolean=false,
    var likesCliked:Boolean=false,
    var likes:Int = 0,
    var twits: Flow<List<TwitData>>
)