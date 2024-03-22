package com.example.twit.ui.screens.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twit.domain.AddTwitUseCase
import com.example.twit.domain.GetTwitUseCase
import com.example.twit.model.TwitData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getTwitUseCase: GetTwitUseCase,private val addTwitUseCase:AddTwitUseCase) :ViewModel(){
    private val _uiState = MutableStateFlow(MainStateUI(twits = listOf() ))
    val uiState: StateFlow<MainStateUI> = _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            getTwitUseCase().onStart {
                _uiState.value = _uiState.value.copy(
                    twits = uiState.value.twits
                )
            }
                .collect { items ->
                   _uiState.value.twits = items

                }
        }
    }



    fun addTwit(description :String){
        viewModelScope.launch{
            addTwitUseCase.invoke(TwitData(description = description))
        }


    }
    fun showAddTwit(show:Boolean){

        _uiState.update { current ->
            current.copy(
                showAddTwit = show
            )
        }
    }

    fun textDescription(description :String){
        _uiState.update { current ->
            current.copy(
                description = description
            )
        }
    }

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


}