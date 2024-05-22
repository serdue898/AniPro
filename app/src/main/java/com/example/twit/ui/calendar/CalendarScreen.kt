package com.example.twit.ui.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController

@Composable
fun Calendar(model: CalendarViewModel = hiltViewModel(), navController: NavController) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<CalendarStateUI>(
        initialValue = CalendarStateUI.Loading,
        key1 = lifecycle,
        key2 = model
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            model.uiState.collect { value = it }
        }
    }
}