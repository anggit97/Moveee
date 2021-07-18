package com.anggit97.model.model

data class RequestToken(
    val expiresAt: String,
    val requestToken: String,
    val success: Boolean
)