package com.anggit97.model.repository

import com.anggit97.model.model.RequestToken
import com.anggit97.model.model.SessionId
import com.anggit97.model.model.SessionIdParam


/**
 * Created by Anggit Prayogo on 23,July,2021
 * GitHub : https://github.com/anggit97
 */
interface AuthRepository {
    suspend fun getRequestToken(): RequestToken
    suspend fun createSessionId(request: SessionIdParam): SessionId
}