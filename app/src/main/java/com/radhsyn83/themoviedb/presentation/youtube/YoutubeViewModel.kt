package com.radhsyn83.themoviedb.presentation.youtube

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radhsyn83.themoviedb.common.Resource
import com.radhsyn83.themoviedb.domain.use_case.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

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