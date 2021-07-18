package com.anggit97.model.domain.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Created by Anggit Prayogo on 18,July,2021
 * GitHub : https://github.com/anggit97
 */
@Serializable
data class SessionIdParam(
    @SerialName("request_token")
    val requestToken: String?
)