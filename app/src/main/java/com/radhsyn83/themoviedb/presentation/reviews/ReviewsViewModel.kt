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

    var id by mutableStateOf(0)
        private set

    init {
        savedStateHandle.get<String>("id")?.let { id ->
            this.id = id.toInt()
            getReviews()
        }
    }

    fun getReviews(isLoadMore: Boolean = false) {
        getReviewsUseCase(id, _state.value.page).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val totalPages = result.data?.totalPage ?: 0
                    val rv = result.data?.result ?: emptyList()
                    var currentPage = _state.value.page
                    val canLoadMore = totalPages != currentPage

                    if (canLoadMore) currentPage++

                    if (!isLoadMore) {
                        _state.value = ReviewsState(
                            reviews = rv,
                            canLoadMore = canLoadMore,
                            page = currentPage
                        )
                    } else {
                        val newMovies = _state.value.reviews + rv
                        _state.value = _state.value.copy(reviews = newMovies, page = currentPage)
                    }
                }
                is Resource.Error -> {
                    _state.value = ReviewsState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    if (!isLoadMore)
                    _state.value = ReviewsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}