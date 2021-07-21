package com.anggit97.data.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AvatarResponse(
    @SerialName("gravatar")
    val gravatarResponse: GravatarResponse?,
    @SerialName("tmdb")
    val tmdbResponse: TmdbResponse?
)