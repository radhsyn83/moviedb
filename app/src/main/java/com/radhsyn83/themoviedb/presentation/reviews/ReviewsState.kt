package com.radhsyn83.themoviedb.presentation.reviews

import com.radhsyn83.themoviedb.domain.model.Review

data class ReviewsState(
    val isLoading: Boolean = false,
    val reviews: List<Review> = emptyList(),
    val error: String = "",
    var page: Int = 1,
    var canLoadMore: Boolean = false
)