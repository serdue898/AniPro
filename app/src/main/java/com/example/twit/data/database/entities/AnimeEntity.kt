package com.example.twit.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

@Entity
data class AnimeEntity(
    @PrimaryKey
    val id:Int,
    val dateEnd: LocalDate,
    val dateStart: LocalDate,
    val episodes: Int = 0,
)
