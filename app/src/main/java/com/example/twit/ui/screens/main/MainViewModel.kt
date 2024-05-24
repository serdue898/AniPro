package com.example.twit.ui.screens.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twit.domain.database.GetAnimeUseCase
import com.example.twit.domain.network.getAnimeRankingUseCase
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
    private val getTwitUseCase: GetAnimeUseCase,
    private val getAnimeRankingUseCase: getAnimeRankingUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<MainStateUI>(MainStateUI.Loading)
    val uiState: StateFlow<MainStateUI> = _uiState.asStateFlow()

    init {
        getAnimes()
    }

    private fun getAnimes() {
        viewModelScope.launch {
            _uiState.update { MainStateUI.Loading }
            _uiState.update {
                try {
                    val res = getAnimeRankingUseCase()
                    MainStateUI.Success( animes = res)
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
                 comments = if (!current.commentsClicked) current.comments.inc() else current.comments.dec(),
                 commentsClicked = !current.commentsClicked
             )
         }
     }
     fun changeReply() {
         _uiState.update { current ->
             current.copy(
                 replies = if (!current.repliesClicked) current.replies.inc() else current.replies.dec(),
                 repliesClicked = !current.repliesClicked
             )
         }
     }
     fun changeLike() {
         _uiState.update { current ->
             current.copy(
                 likes = if (!current.likesClicked) current.likes.inc() else current.likes.dec(),
                 likesClicked = !current.likesClicked
             )
         }
     }

     */


}