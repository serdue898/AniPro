package com.example.anipro.ui.screens.calendar

import com.example.anipro.model.AnimeData

open class CalendarStateUI {
    data class Success(
        val animeList: List<AnimeData>,
        val animesShowList: List<AnimeData>
    ) : CalendarStateUI()

    object Error : CalendarStateUI()
    object Loading : CalendarStateUI()

}