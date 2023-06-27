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

     fun getGenres(isLoadMore: Boolean = false) {
        getGenresUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val totalPages = result.data?.totalPage ?: 0
                    val gr = result.data?.result ?: emptyList()
                    var currentPage = _state.value.page
                    val canLoadMore = totalPages != currentPage

                    if (canLoadMore) currentPage++

                    if (!isLoadMore) {
                        _state.value = GenresState(
                            genres = gr,
                            canLoadMore = canLoadMore,
                            page = currentPage
                        )
                    } else {
                        val newMovies = _state.value.genres + gr
                        _state.value = _state.value.copy(genres = newMovies, page = currentPage)
                    }
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