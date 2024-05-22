package com.example.twit.domain.database

import com.example.twit.data.database.AnimeRepository
import com.example.twit.model.TwitData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimeUseCase @Inject constructor(val repository: AnimeRepository) {
    operator fun invoke():Flow<List<TwitData>> = repository.twits
}