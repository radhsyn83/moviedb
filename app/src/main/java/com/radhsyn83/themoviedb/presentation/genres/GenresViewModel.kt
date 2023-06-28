package com.radhsyn83.themoviedb.presentation.genres

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radhsyn83.themoviedb.common.Resource
import com.radhsyn83.themoviedb.domain.use_case.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase
) : ViewModel() {

    private val _state = mutableStateOf(GenresState())
    val state: State<GenresState> = _state

    init {
        getGenres()
    }

     private fun getGenres() {
        getGenresUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = GenresState(genres = result.data ?: listOf())
                }
                is Resource.Error -> {
                    _state.value = GenresState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = GenresState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}