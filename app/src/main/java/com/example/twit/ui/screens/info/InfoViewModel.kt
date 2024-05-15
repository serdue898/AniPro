package com.example.twit.ui.screens.info


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twit.domain.database.GetTwitUseCase
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
class InfoViewModel @Inject constructor(
    private val getTwitUseCase: GetTwitUseCase,
    private val getAnimeRankingUseCase: getAnimeRankingUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<InfoStateUI>(InfoStateUI.Loading)
    val uiState: StateFlow<InfoStateUI> = _uiState.asStateFlow()



    fun getAnimeInfo(id: String) {
        viewModelScope.launch {
            _uiState.update { InfoStateUI.Loading }
            _uiState.update {
                try {
                    val res = getAnimeRankingUseCase()
                    InfoStateUI.Success(twits = getTwitUseCase.invoke(), animes = res)
                } catch (e: IOException) {
                    InfoStateUI.Error
                } catch (e: HttpException) {
                    InfoStateUI.Error
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