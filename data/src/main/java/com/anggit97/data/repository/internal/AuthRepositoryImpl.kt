package com.anggit97.data.repository.internal

import com.anggit97.data.api.apiservice.AuthApiService
import com.anggit97.data.repository.internal.mapper.toRequestToken
import com.anggit97.data.repository.internal.mapper.toSessionId
import com.anggit97.model.model.RequestToken
import com.anggit97.model.model.SessionId
import com.anggit97.model.model.SessionIdParam
import com.anggit97.model.repository.AuthRepository
import com.anggit97.session.SessionManagerStore


/**
 * Created by Anggit Prayogo on 23,July,2021
 * GitHub : https://github.com/anggit97
 */
class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val sessionManager: SessionManagerStore,
) : AuthRepository {

    override suspend fun getRequestToken(): RequestToken {
        return authApiService.getRequestToken().toRequestToken()
    }

    override suspend fun createSessionId(request: SessionIdParam): SessionId {
        val response = authApiService.createSessionId(request)
        sessionManager.setSessionId(response.sessionId ?: "-")
        sessionManager.setLogin(true)
        return response.toSessionId()
    }
}