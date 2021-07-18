package com.anggit97.model.domain.auth

import com.anggit97.model.model.RequestToken
import com.anggit97.model.model.SessionId
import com.anggit97.model.model.SessionIdParam
import com.anggit97.model.repository.MovieeeRepository
import javax.inject.Inject

class AuthUseCaseImpl @Inject constructor(
    private val movieeeRepository: MovieeeRepository
) : AuthUseCase {

    override suspend fun getRequestToken(): RequestToken {
        return movieeeRepository.getRequestToken()
    }

    override suspend fun createSessionId(request: SessionIdParam): SessionId {
        return movieeeRepository.createSessionId(request)
    }
}
