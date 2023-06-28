package com.radhsyn83.themoviedb.presentation.youtube

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class YoutubeViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(YoutubeState())
    val state: State<YoutubeState> = _state

    init {
        savedStateHandle.get<String>("id").let {
            _state.value = YoutubeState(youtubeId = it)
        }
    }
}