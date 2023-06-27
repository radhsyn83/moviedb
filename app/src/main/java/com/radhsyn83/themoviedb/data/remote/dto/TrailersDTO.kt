package com.radhsyn83.themoviedb.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.radhsyn83.themoviedb.domain.model.Trailer

data class TrailersDTO(
    @SerializedName("status_message")
    var statusMessage: String?,
    var success: Boolean? = true,
    val id: Int,
    val youtube: List<Youtube>
) {
    data class Youtube(
        val source: String
    )
}

fun TrailersDTO.toTrailers(): List<Trailer> {
    var dt: List<Trailer> = emptyList()
    youtube.map {
        dt += Trailer(
            youtubeSource = it.source
        )
    }
    return dt
}