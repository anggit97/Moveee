package com.anggit97.model.domain.auth

data class RequestToken(
    val expiresAt: String,
    val requestToken: String,
    val success: Boolean
)