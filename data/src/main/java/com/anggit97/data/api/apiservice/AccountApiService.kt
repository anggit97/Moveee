package com.anggit97.data.api.apiservice

import com.anggit97.data.api.response.AccountResponse
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Anggit Prayogo on 23,July,2021
 * GitHub : https://github.com/anggit97
 */
interface AccountApiService {

    @GET("account")
    suspend fun getAccount(@Query("session_id") sessionId: String): AccountResponse
}