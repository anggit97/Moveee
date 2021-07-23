package com.anggit97.data.api.apiservice

import com.anggit97.data.api.response.CreateSessionIdResponse
import com.anggit97.data.api.response.RequestTokenResponse
import com.anggit97.model.model.SessionIdParam
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


/**
 * Created by Anggit Prayogo on 23,July,2021
 * GitHub : https://github.com/anggit97
 */
interface AuthApiService {

    @GET("authentication/token/new")
    suspend fun getRequestToken(): RequestTokenResponse

    @POST("authentication/session/new")
    suspend fun createSessionId(@Body request: SessionIdParam): CreateSessionIdResponse
}