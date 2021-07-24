package com.anggit97.data.repository.internal.mapper.auth

import com.anggit97.data.api.response.RequestTokenResponse
import com.anggit97.model.model.RequestToken


/**
 * Created by Anggit Prayogo on 24,July,2021
 * GitHub : https://github.com/anggit97
 */
interface RequestTokenMapper {
    fun mapToRequestToken(data: RequestTokenResponse): RequestToken
    fun mapToRequestTokenResponse(data: RequestToken): RequestTokenResponse
}
