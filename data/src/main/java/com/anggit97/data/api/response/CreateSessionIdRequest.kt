package com.anggit97.data.api.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateSessionIdRequest(
    @SerialName("request_token")
    val requestToken: String?
)