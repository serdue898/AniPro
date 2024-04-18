package com.example.twit.ui.screens.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twit.domain.database.GetTwitUseCase
import com.example.twit.domain.network.getAnimeRankingUseCase
import com.example.twit.domain.network.getAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTwitUseCase: GetTwitUseCase,
    private val getAnimeUseCase: getAnimeUseCase,
    private val getAnimeRankingUseCase: getAnimeRankingUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<MainStateUI>(MainStateUI.Loading)
    val uiState: StateFlow<MainStateUI> = _uiState.asStateFlow()

    init {
        getAnimes()
    }

    fun getAnimes() {
        viewModelScope.launch {
            _uiState.update { MainStateUI.Loading }
            _uiState.update {
                try {
                    val res = getAnimeRankingUseCase()
                    MainStateUI.Succes(twits = getTwitUseCase.invoke(), animes = res)
                } catch (e: IOException) {
                    MainStateUI.Error
                } catch (e: HttpException) {
                    MainStateUI.Error
                }
            }
        }

    }

    /*
     fun newComent() {
         _uiState.update { current ->
             current.copy(
                 comments = if (!current.commentsCliked) current.comments.inc() else current.comments.dec(),
                 commentsCliked = !current.commentsCliked
             )
         }
     }
     fun changeReply() {
         _uiState.update { current ->
             current.copy(
                 replies = if (!current.repliesCliked) current.replies.inc() else current.replies.dec(),
                 repliesCliked = !current.repliesCliked
             )
         }
     }
     fun changeLike() {
         _uiState.update { current ->
             current.copy(
                 likes = if (!current.likesCliked) current.likes.inc() else current.likes.dec(),
                 likesCliked = !current.likesCliked
             )
         }
     }

     */


}