package com.radhsyn83.themoviedb.presentation.trailers

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radhsyn83.themoviedb.common.Resource
import com.radhsyn83.themoviedb.domain.use_case.GetTrailersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TrailersViewModel @Inject constructor(
    private val getTrailersUseCase: GetTrailersUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(TrailersState())
    val state: State<TrailersState> = _state
    var title by mutableStateOf("Movies")
        private set

    init {
        savedStateHandle.get<String>("id")?.let { id ->
            getMovies(id.toInt())
        }
    }

    private fun getMovies(id: Int) {
        getTrailersUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = TrailersState(trailers = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = TrailersState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = TrailersState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}