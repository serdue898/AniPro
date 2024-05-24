package com.example.twit.ui.screens.calendar

import com.example.twit.model.AnimeData

open class CalendarStateUI {
        data class Success(val animeList: List<AnimeData>
        ): CalendarStateUI()
        object Error : CalendarStateUI()
        object Loading : CalendarStateUI()

}