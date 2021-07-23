package com.anggit97.model.domain.auth

import com.anggit97.model.model.RequestToken
import com.anggit97.model.model.SessionId
import com.anggit97.model.model.SessionIdParam
import com.anggit97.model.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : AuthUseCase {

    override suspend fun getRequestToken(): Flow<RequestToken> {
        return authRepository.getRequestToken()
    }

    override suspend fun createSessionId(request: SessionIdParam): Flow<SessionId> {
        return authRepository.createSessionId(request)
    }
}
