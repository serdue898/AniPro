package com.example.twit.ui.screens.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.twit.domain.GetTwitUseCase
import com.example.twit.ui.screens.main.MainStateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState.asStateFlow()


    fun emailText(text:String){
        _uiState.update { current ->
            current.copy(email = text)
        }
    }
    fun passwordText(text:String){
        _uiState.update { current ->
            current.copy(password = text)
        }
    }
    fun changeVisibility(visibility:Boolean){
        _uiState.update { current ->
            current.copy(showPassword = !visibility)
        }
    }
    fun checkLogin(email:String,password:String){
        val checkEmail=Patterns.EMAIL_ADDRESS.matcher(email).matches()
        _uiState.update { current ->
            current.copy(LogInEnable = ( password.length>6 && checkEmail))
        }

    }


}