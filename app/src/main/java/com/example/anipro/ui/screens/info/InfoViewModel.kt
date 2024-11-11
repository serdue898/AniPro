package com.example.anipro.ui.screens.info


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anipro.domain.database.AddAnimeUseCase
import com.example.anipro.domain.database.GetAnimeByIdUseCase
import com.example.anipro.domain.network.GetAnimeInfoUseCase
import com.example.anipro.model.AnimeData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val getAnimeInfoUseCase: GetAnimeInfoUseCase,
    private val addAnimeUseCase: AddAnimeUseCase,
    private val getAnimeByIdUseCase: GetAnimeByIdUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<InfoStateUI>(InfoStateUI.Loading)
    val uiState: StateFlow<InfoStateUI> = _uiState.asStateFlow()


    fun getAnimeInfo(id: Int) {
        viewModelScope.launch {
            _uiState.update { InfoStateUI.Loading }
            _uiState.update {
                try {
                    InfoStateUI.Success(anime = getAnimeInfoUseCase.invoke(anime_id = id))
                } catch (e: IOException) {
                    InfoStateUI.Error
                } catch (e: HttpException) {
                    InfoStateUI.Error
                }
            }
        }

    }
    fun addAnime(endDate: LocalDate) {
        viewModelScope.launch {
            if (_uiState.value is InfoStateUI.ShowPopup) {
                val anime = (_uiState.value as InfoStateUI.ShowPopup).anime
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val startDate = LocalDate.parse(anime.start_date, formatter)
                val animeData = AnimeData(
                    id = anime.id,
                    dateEnd = endDate,
                    dateStart = startDate,
                    title = anime.title,
                    image = anime.main_picture.medium,
                )
                addAnimeUseCase.invoke(animeData)
                _uiState.value = InfoStateUI.Success(anime)
            }
        }
    }

    fun addAnime(episodes: Int) {
        viewModelScope.launch {
            if (_uiState.value is InfoStateUI.ShowPopup) {
                val anime = (_uiState.value as InfoStateUI.ShowPopup).anime
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val startDate = LocalDate.parse(anime.start_date, formatter)
                val endDate = startDate.plusWeeks(episodes.toLong())
                val animeData = AnimeData(
                    id = anime.id,
                    dateEnd = endDate,
                    dateStart = startDate,
                    title = anime.title,
                    image = anime.main_picture.medium,
                )
                addAnimeUseCase.invoke(animeData)
                _uiState.value = InfoStateUI.Success(anime)
            }
        }
    }
    fun showPopUp() {
        viewModelScope.launch {
            val anime = (_uiState.value as InfoStateUI.Success).anime
            val episodes = getAnimeByIdUseCase(anime.id).firstOrNull()?.firstOrNull()?.episodes ?: 0
            _uiState.value = InfoStateUI.ShowPopup(anime,episodes)
        }
    }

    fun dismissPopUp() {
        _uiState.value = InfoStateUI.Success((_uiState.value as InfoStateUI.ShowPopup).anime)
    }


}