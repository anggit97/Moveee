package com.anggit97.data.api.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GravatarResponse(
    @SerialName("hash")
    val hash: String?
)