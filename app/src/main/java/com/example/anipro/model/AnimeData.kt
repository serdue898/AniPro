package com.example.anipro.model

import java.time.LocalDate

data class AnimeData(
    val id: Int,
    val dateEnd: LocalDate,
    val dateStart: LocalDate,
    val episodes: Int = 0
)
