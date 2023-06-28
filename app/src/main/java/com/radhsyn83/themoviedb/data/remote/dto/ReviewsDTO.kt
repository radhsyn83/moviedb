package com.radhsyn83.themoviedb.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.radhsyn83.themoviedb.domain.model.Page
import com.radhsyn83.themoviedb.domain.model.Review

data class ReviewsDTO(
    @SerializedName("status_message")
    var statusMessage: String?,
    @SerializedName("success")
    var success: Boolean? = true,
    val id: Int,
    val page: Int,
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
) {
    data class Result(
        @SerializedName("author")
        val author: String,
        @SerializedName("author_details")
        val authorDetails: AuthorDetails,
        @SerializedName("content")
        val content: String,
    ) {
        data class AuthorDetails(
            @SerializedName("name")
            val name: String,
            @SerializedName("rating")
            val rating: Double,
        )
    }
}

fun ReviewsDTO.toReviews(): Page<List<Review>> {
    var dt: List<Review> = emptyList()
    results.map {
        dt += Review(
            author = it.author,
            content = it.content,
            authorDetails = Review.AuthorDetails(
                name = it.authorDetails.name,
                rating = it.authorDetails.rating
            )
        )
    }
    return Page(
        totalPage = totalPages ?: 1,
        page = this.page ?: 1,
        result = dt
    )
}