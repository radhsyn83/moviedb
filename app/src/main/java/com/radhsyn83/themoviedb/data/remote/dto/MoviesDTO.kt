package com.radhsyn83.themoviedb.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.radhsyn83.themoviedb.domain.model.Movie
import com.radhsyn83.themoviedb.domain.model.Page

data class MoviesDTO(
    @SerializedName("page")
    var page: Int?,
    @SerializedName("total_pages")
    var totalPages: Int?,
    @SerializedName("results")
    var results: List<Result>?,
    @SerializedName("status_message")
    var statusMessage: String?,
    @SerializedName("success")
    var success: Boolean? = true,

    ) {
    data class Result(
        @SerializedName("id")
        var id: Int?,
        @SerializedName("poster_path")
        var posterPath: String?,
        var title: String?,
        @SerializedName("vote_average")
        var voteAverage: String?,
    )

}

fun MoviesDTO.toMovies(): Page<List<Movie>> {
    var dt: List<Movie> = emptyList()
    results?.map {
        dt += Movie(
            id = it.id,
            posterPath = it.posterPath,
            voteAverage = it.voteAverage,
            title = it.title
        )
    }
    return Page(
        totalPage = totalPages ?: 1,
        page = this.page ?: 1,
        result = dt
    )
}