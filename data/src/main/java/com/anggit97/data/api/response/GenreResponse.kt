package com.anggit97.data.api.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?
)