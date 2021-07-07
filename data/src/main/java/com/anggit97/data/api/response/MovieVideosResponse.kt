package com.anggit97.data.api.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieVideosResponse(
    @SerialName("id")
    val id: Int?,
    @SerialName("results")
    val results: List<VideoResponse>?
)