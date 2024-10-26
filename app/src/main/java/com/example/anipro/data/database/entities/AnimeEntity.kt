package com.example.anipro.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class AnimeEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val image: String,
    val dateEnd: LocalDate,
    val dateStart: LocalDate,
    val episodes: Int = 0,
)
