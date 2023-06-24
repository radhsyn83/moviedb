package com.radhsyn83.themoviedb.data.models


import com.google.gson.annotations.SerializedName

data class TrailersModel(
    @SerializedName("status_code")
    var statusCode: Int?,
    @SerializedName("status_message")
    var statusMessage: String?,
    @SerializedName("success")
    var success: Boolean? = true,
    @SerializedName("id")
    val id: Int,
    @SerializedName("quicktime")
    val quicktime: List<Any>,
    @SerializedName("youtube")
    val youtube: List<Youtube>
) {
    data class Youtube(
        @SerializedName("name")
        val name: String,
        @SerializedName("size")
        val size: String,
        @SerializedName("source")
        val source: String,
        @SerializedName("type")
        val type: String
    )
}