package com.anggit97.model.repository

import com.anggit97.model.model.Account


/**
 * Created by Anggit Prayogo on 23,July,2021
 * GitHub : https://github.com/anggit97
 */
interface AccountRepository {
    suspend fun getAccount(): Account
}