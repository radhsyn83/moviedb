package com.radhsyn83.themoviedb.presentation.reviews

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radhsyn83.themoviedb.common.Resource
import com.radhsyn83.themoviedb.domain.use_case.GetReviewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val getReviewsUseCase: GetReviewsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(ReviewsState())
    val state: State<ReviewsState> = _state

    var page by mutableStateOf(1)
        private set

    init {
        savedStateHandle.get<String>("id")?.let { id ->
            getMovies(id.toInt())
        }
    }

    private fun getMovies(id: Int) {
        getReviewsUseCase(id, page).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ReviewsState(reviews = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = ReviewsState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = ReviewsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}