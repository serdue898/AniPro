package com.example.anipro.domain.database

import com.example.anipro.data.database.AnimeRepository
import com.example.anipro.model.AnimeData
import javax.inject.Inject

class AddAnimeUseCase @Inject constructor(private val repository: AnimeRepository) {
    suspend operator fun invoke(animeData: AnimeData) {
        repository.addAnimeL(animeData)
    }
}