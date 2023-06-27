package com.radhsyn83.themoviedb.domain.model

data class Review(
    val author: String,
    val authorDetails: AuthorDetails,
    val content: String,
) {
    data class AuthorDetails(
        val name: String,
        val rating: Double,
    )
}