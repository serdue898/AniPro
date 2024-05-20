package com.example.twit.domain.database

import com.example.twit.data.database.TwitRepository
import com.example.twit.model.TwitData
import javax.inject.Inject

class AddTwitUseCase @Inject constructor(private val repository: TwitRepository){
    suspend operator fun invoke(twitData: TwitData){
        repository.add(twitData)
    }
}