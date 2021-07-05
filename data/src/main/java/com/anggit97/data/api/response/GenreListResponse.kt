package com.anggit97.data.api.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreListResponse(
    @SerialName("genres")
    val genres: List<GenreResponse>?
)