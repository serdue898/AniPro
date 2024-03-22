package com.example.twit.domain.network

import com.example.twit.data.network.AnimeRepository
import com.example.twit.data.network.response.AnimeResponse
import javax.inject.Inject

class getAnimeUseCase @Inject constructor(private val repository: AnimeRepository) {
    suspend operator fun invoke():AnimeResponse{
        return repository.getAnime()

    }
}