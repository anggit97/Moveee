package com.anggit97.model.domain.account

import com.anggit97.model.model.Account


/**
 * Created by Anggit Prayogo on 21,July,2021
 * GitHub : https://github.com/anggit97
 */
interface AccountUseCase {
    suspend fun getAccount(): Account
}