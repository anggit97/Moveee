package com.anggit97.model.repository

import com.anggit97.model.model.RequestToken
import com.anggit97.model.model.SessionId
import com.anggit97.model.model.SessionIdParam
import kotlinx.coroutines.flow.Flow


/**
 * Created by Anggit Prayogo on 23,July,2021
 * GitHub : https://github.com/anggit97
 */
interface AuthRepository {
    suspend fun getRequestToken(): Flow<RequestToken>
    suspend fun createSessionId(request: SessionIdParam): Flow<SessionId>
}