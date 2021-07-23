package com.anggit97.model.domain.account

import com.anggit97.model.model.Account
import com.anggit97.model.repository.AccountRepository
import javax.inject.Inject

class AccountUseCaseImpl @Inject constructor(
    private val accountRepository: AccountRepository
) : AccountUseCase {

    override suspend fun getAccount(): Account {
        return accountRepository.getAccount()
    }
}