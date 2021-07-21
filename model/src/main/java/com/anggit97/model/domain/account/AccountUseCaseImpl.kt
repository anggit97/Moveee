package com.anggit97.model.domain.account

import com.anggit97.model.model.Account
import com.anggit97.model.repository.MovieeeRepository
import javax.inject.Inject

class AccountUseCaseImpl @Inject constructor(
    private val repository: MovieeeRepository
) : AccountUseCase {

    override suspend fun getAccount(): Account {
        return repository.getAccount()
    }
}