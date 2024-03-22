package com.example.twit.domain.database

import com.example.twit.data.database.TwitRepository
import com.example.twit.data.database.entities.TwitEntity
import com.example.twit.model.TwitData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTwitUseCase @Inject constructor(val repository: TwitRepository) {
    operator fun invoke():Flow<List<TwitData>> = repository.twits
}