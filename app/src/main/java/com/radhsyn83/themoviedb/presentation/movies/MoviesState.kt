package com.radhsyn83.themoviedb.presentation.movies

import com.radhsyn83.themoviedb.domain.model.Movie

data class MoviesState(
    val isLoading: Boolean = false,
    var movies: List<Movie> = emptyList(),
    val error: String = "",
    var page: Int = 1,
    var canLoadMore: Boolean = false
)