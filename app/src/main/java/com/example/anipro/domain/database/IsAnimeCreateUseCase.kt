package com.example.anipro.domain.database

import com.example.anipro.data.database.AnimeRepository
import com.example.anipro.model.AnimeData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsAnimeCreateUseCase @Inject constructor(val repository: AnimeRepository) {
    operator fun invoke(id:Int): Flow<Boolean> = repository.isAnimeCreate(id)
}