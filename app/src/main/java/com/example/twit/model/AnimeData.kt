package com.example.twit.model

import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

data class AnimeData (
        val id:Int,
        val dateEnd: LocalDate,
        val dateStart: LocalDate,
        val episodes: Int = 0
)
