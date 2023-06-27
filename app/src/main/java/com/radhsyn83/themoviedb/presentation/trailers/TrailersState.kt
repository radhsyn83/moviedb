package com.radhsyn83.themoviedb.presentation.trailers

import com.radhsyn83.themoviedb.domain.model.Trailer

data class TrailersState(
    val isLoading: Boolean = false,
    val trailers: List<Trailer> = emptyList(),
    val error: String = ""
)