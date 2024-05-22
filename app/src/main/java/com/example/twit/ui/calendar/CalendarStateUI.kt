package com.example.twit.ui.calendar

import com.example.twit.model.AnimeItem

open class CalendarStateUI {
        data class Success(val animeList: List<AnimeItem>
        ):CalendarStateUI()
        object Error : CalendarStateUI()
        object Loading : CalendarStateUI()

}