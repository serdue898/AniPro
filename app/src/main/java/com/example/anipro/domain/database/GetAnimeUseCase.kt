package com.example.anipro.domain.database

import com.example.anipro.data.database.AnimeRepository
import com.example.anipro.model.AnimeData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimeUseCase @Inject constructor(val repository: AnimeRepository) {
    operator fun invoke(): Flow<List<AnimeData>> = repository.animes
}