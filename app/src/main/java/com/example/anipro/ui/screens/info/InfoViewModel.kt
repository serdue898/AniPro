package com.example.anipro.ui.screens.info


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anipro.domain.database.AddAnimeUseCase
import com.example.anipro.domain.database.GetAnimeByIdUseCase
import com.example.anipro.domain.database.IsAnimeCreateUseCase
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
    private val isAnimeCreateUseCase: IsAnimeCreateUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<InfoStateUI>(InfoStateUI.Loading)
    val uiState: StateFlow<InfoStateUI> = _uiState.asStateFlow()


    fun getAnimeInfo(id: Int) {
        viewModelScope.launch {
            _uiState.update { InfoStateUI.Loading }
            _uiState.update {
                try {
                    val isCreate = isAnimeCreateUseCase(id).firstOrNull() ?: false
                    InfoStateUI.Success(anime = getAnimeInfoUseCase.invoke(anime_id = id), isAnimeCreate = isCreate)
                } catch (e: IOException) {
                    InfoStateUI.Error
                } catch (e: HttpException) {
                    InfoStateUI.Error
                }
            }
        }

    }

}