package com.example.twit.ui.screens.info


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twit.domain.database.AddAnimeUseCase
import com.example.twit.domain.network.getAnimeInfoUseCase
import com.example.twit.model.AnimeData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val getAnimeInfoUseCase: getAnimeInfoUseCase,
    private val addAnimeUseCase: AddAnimeUseCase
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

    fun addAnime(episodes: Int) {

        viewModelScope.launch {
            if (_uiState.value is InfoStateUI.ShowPopup) {
                val anime = (_uiState.value as InfoStateUI.ShowPopup).anime
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val startDate = LocalDate.parse(anime.start_date, formatter)
                val endDate = startDate.plusWeeks(episodes.toLong() )
                val animeData = AnimeData(
                    id = anime.id,
                    episodes = episodes,
                    dateEnd = endDate,
                    dateStart = startDate
                )
                addAnimeUseCase.invoke(animeData)
            }
        }
    }
    fun showPopUp() {
        val anime = (_uiState.value as InfoStateUI.Success).anime
        _uiState.value = InfoStateUI.ShowPopup(anime)
    }
    fun dismissPopUp() {
        _uiState.value = InfoStateUI.Success((_uiState.value as InfoStateUI.ShowPopup).anime)
    }


}