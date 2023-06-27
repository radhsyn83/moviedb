package com.radhsyn83.themoviedb.presentation.movies

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radhsyn83.themoviedb.common.Resource
import com.radhsyn83.themoviedb.domain.use_case.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(MoviesState())
    val state: State<MoviesState> = _state

    var id by mutableStateOf(0)
        private set

    var title by mutableStateOf("Movies")
        private set

    init {
        savedStateHandle.get<String>("id")?.let { id ->
            this.id = id.toInt()
            getMovies()
        }
        savedStateHandle.get<String>("title")?.let { title ->
            this.title = title
        }
    }

    fun getMovies(isLoadMore: Boolean = false) {
        getMoviesUseCase(id, _state.value.page).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val totalPages = result.data?.totalPage ?: 0
                    val mv = result.data?.result ?: emptyList()
                    var currentPage = _state.value.page
                    val canLoadMore = totalPages != currentPage

                    if (canLoadMore) currentPage++

                    if (!isLoadMore) {
                        _state.value = MoviesState(
                            movies = mv,
                            canLoadMore = canLoadMore,
                            page = currentPage
                        )
                    } else {
                        val newMovies = _state.value.movies + mv
                        _state.value = _state.value.copy(movies = newMovies, page = currentPage)
                    }
                }

                is Resource.Error -> {
                    _state.value = MoviesState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }

                is Resource.Loading -> {
                    if (!isLoadMore)
                        _state.value = MoviesState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}