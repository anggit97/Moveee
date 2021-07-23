package com.anggit97.data.repository.internal

import com.anggit97.data.api.apiservice.AccountApiService
import com.anggit97.data.repository.internal.mapper.toAccount
import com.anggit97.model.model.Account
import com.anggit97.model.repository.AccountRepository
import com.anggit97.session.SessionManagerStore
import kotlinx.coroutines.flow.first


/**
 * Created by Anggit Prayogo on 23,July,2021
 * GitHub : https://github.com/anggit97
 */
class AccountRepositoryImpl(
    private val accountApiService: AccountApiService,
    private val sessionManager: SessionManagerStore
) : AccountRepository{

    override suspend fun getAccount(): Account {
        val sessionId = sessionManager.getSessionId().first()
        return accountApiService.getAccount(sessionId = sessionId).toAccount()
    }
}