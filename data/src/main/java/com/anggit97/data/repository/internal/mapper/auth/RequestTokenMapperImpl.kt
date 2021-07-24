package com.anggit97.data.repository.internal.mapper.auth

import com.anggit97.data.api.response.RequestTokenResponse
import com.anggit97.model.model.RequestToken
import javax.inject.Inject

class RequestTokenMapperImpl @Inject constructor() : RequestTokenMapper {

    override fun mapToRequestToken(data: RequestTokenResponse): RequestToken {
        return RequestToken(
            expiresAt = data.expiresAt ?: "",
            requestToken = data.requestToken ?: "",
            success = data.success ?: false
        )
    }

    override fun mapToRequestTokenResponse(data: RequestToken): RequestTokenResponse {
        return RequestTokenResponse(
            expiresAt = data.expiresAt,
            requestToken = data.requestToken,
            success = data.success
        )
    }
}