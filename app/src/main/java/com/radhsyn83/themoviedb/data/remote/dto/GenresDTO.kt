package com.radhsyn83.themoviedb.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.radhsyn83.themoviedb.domain.model.Genre
import com.radhsyn83.themoviedb.domain.model.Page

data class GenresDTO(
    @SerializedName("page")
    var page: Int?,
    @SerializedName("total_pages")
    var totalPages: Int?,
    @SerializedName("status_code")
    var statusCode: Int?,
    @SerializedName("status_message")
    var statusMessage: String?,
    @SerializedName("success")
    var success: Boolean? = true,
    @SerializedName("genres")
    var genres: List<Result>
) {
    data class Result(
        var id: Int?,
        var name: String?
    )
}

fun GenresDTO.toGenres(): Page<List<Genre>> {
    var dt: List<Genre> = emptyList()
    genres.map {
        dt += Genre(
            id = it.id,
            name = it.name
        )
    }
    return Page(
        totalPage = totalPages ?: 1,
        page = this.page ?: 1,
        result = dt
    )
}