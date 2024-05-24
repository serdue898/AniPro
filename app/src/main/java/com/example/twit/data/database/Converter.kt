package com.example.twit.data.database

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime

class Converter {
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): String? {
        return date?.toString()
    }
}