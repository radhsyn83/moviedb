package com.radhsyn83.themoviedb.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.radhsyn83.themoviedb.domain.model.Detail

data class DetailDTO(
    @SerializedName("status_message")
    var statusMessage: String?,
    @SerializedName("success")
    var success: Boolean? = true,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    var id: Int?,
    @SerializedName("original_title")
    var originalName: String?,
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("poster_path")
    var posterPath: String?,
    var status: String?,
    @SerializedName("vote_average")
    var voteAverage: Double?,
)

fun DetailDTO.toDetail() : Detail = Detail(
    id = id,
    backdropPath = backdropPath,
    originalName = originalName,
    overview = overview,
    posterPath = posterPath,
    status = status,
    voteAverage = voteAverage
)