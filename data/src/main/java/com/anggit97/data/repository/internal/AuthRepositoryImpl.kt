package com.anggit97.data.repository.internal

import com.anggit97.data.api.apiservice.AuthApiService
import com.anggit97.data.repository.internal.mapper.auth.RequestTokenMapper
import com.anggit97.data.repository.internal.mapper.toRequestToken
import com.anggit97.data.repository.internal.mapper.toSessionId
import com.anggit97.model.model.RequestToken
import com.anggit97.model.model.SessionId
import com.anggit97.model.model.SessionIdParam
import com.anggit97.model.repository.AuthRepository
import com.anggit97.session.SessionManagerStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


/**
 * Created by Anggit Prayogo on 23,July,2021
 * GitHub : https://github.com/anggit97
 */
class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val sessionManager: SessionManagerStore,
    private val mapper: RequestTokenMapper
) : AuthRepository {

    override suspend fun getRequestToken(): Flow<RequestToken> {
        return flow {
            emit(mapper.mapToRequestToken(authApiService.getRequestToken()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun createSessionId(request: SessionIdParam): Flow<SessionId> {
        return flow {
            val result = authApiService.createSessionId(request)
            sessionManager.apply {
                setSessionId(result.sessionId ?: "-")
                setLogin(true)
            }
            emit(result.toSessionId())
        }.flowOn(Dispatchers.IO)
    }
}