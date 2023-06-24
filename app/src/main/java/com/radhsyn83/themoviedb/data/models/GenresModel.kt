package com.radhsyn83.themoviedb.data.models

import com.google.gson.annotations.SerializedName

data class GenresModel(
    @SerializedName("status_code")
    var statusCode: Int?,
    @SerializedName("status_message")
    var statusMessage: String?,
    @SerializedName("success")
    var success: Boolean? = true,
    @SerializedName("genres")
    var genres: ArrayList<Result>
) {
    data class Result(
        var id: Int?,
        var name: String?
    )
}