package com.anggit97.session

import kotlinx.coroutines.flow.Flow


/**
 * Created by Anggit Prayogo on 19,July,2021
 * GitHub : https://github.com/anggit97
 */
interface SessionManagerStore {
    fun getSessionId(): Flow<String>
    suspend fun setSessionId(sessionId: String)
}