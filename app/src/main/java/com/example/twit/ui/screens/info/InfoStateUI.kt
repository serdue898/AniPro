package com.example.twit.ui.screens.info

import com.example.twit.model.AnimeItem
import com.example.twit.model.TwitData
import kotlinx.coroutines.flow.Flow

open class InfoStateUI {

        data class Success(
            var comments:Int = 0,
            var commentsClicked:Boolean=false,
            var replies:Int = 0,
            var repliesClicked:Boolean=false,
            var likesClicked:Boolean=false,
            var likes:Int = 0,
            var anime:AnimeItem
        ):InfoStateUI()
        object Error : InfoStateUI()
        object Loading : InfoStateUI()



}