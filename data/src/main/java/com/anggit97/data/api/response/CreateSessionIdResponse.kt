package com.anggit97.data.api.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateSessionIdResponse(
    @SerialName("session_id")
    val sessionId: String?,
    @SerialName("success")
    val success: Boolean?
)