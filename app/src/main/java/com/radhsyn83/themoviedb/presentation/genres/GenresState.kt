package com.radhsyn83.themoviedb.presentation.genres

import com.radhsyn83.themoviedb.domain.model.Genre


data class GenresState(
    val isLoading: Boolean = false,
    val genres: List<Genre> = emptyList(),
    val error: String = ""
)