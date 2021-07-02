package com.anggit97.data.api.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsListResponse(
    @SerialName("cast")
    val cast: List<Cast>?,
    @SerialName("crew")
    val crew: List<Crew>?,
    @SerialName("id")
    val id: Int?
)