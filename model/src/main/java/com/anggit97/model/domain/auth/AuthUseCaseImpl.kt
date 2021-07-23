package com.anggit97.model.domain.auth

import com.anggit97.model.model.RequestToken
import com.anggit97.model.model.SessionId
import com.anggit97.model.model.SessionIdParam
import com.anggit97.model.repository.AuthRepository
import com.anggit97.model.repository.MovieRepository
import javax.inject.Inject

class AuthUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : AuthUseCase {

    override suspend fun getRequestToken(): RequestToken {
        return authRepository.getRequestToken()
    }

    override suspend fun createSessionId(request: SessionIdParam): SessionId {
        return authRepository.createSessionId(request)
    }
}
