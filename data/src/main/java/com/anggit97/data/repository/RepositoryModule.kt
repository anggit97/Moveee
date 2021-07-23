package com.anggit97.data.repository

import com.anggit97.data.api.apiservice.AccountApiService
import com.anggit97.data.api.apiservice.AuthApiService
import com.anggit97.data.api.apiservice.MovieApiService
import com.anggit97.data.db.MovieeeDatabase
import com.anggit97.data.repository.internal.MovieeeRepositoryImpl
import com.anggit97.model.repository.MovieeeRepository
import com.anggit97.session.SessionManagerStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by Anggit Prayogo on 03,July,2021
 * GitHub : https://github.com/anggit97
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMoveeeRepository(
        movieDatabase: MovieeeDatabase,
        movieApiService: MovieApiService,
        authApiService: AuthApiService,
        accountApiService: AccountApiService,
        sessionManagerStore: SessionManagerStore
    ): MovieeeRepository {
        return MovieeeRepositoryImpl(
            local = movieDatabase,
            movieApiService = movieApiService,
            authApiService = authApiService,
            accountApiService = accountApiService,
            sessionManager = sessionManagerStore
        )
    }
}