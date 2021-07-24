package com.anggit97.data.di

import com.anggit97.data.api.apiservice.AccountApiService
import com.anggit97.data.api.apiservice.AuthApiService
import com.anggit97.data.api.apiservice.MovieApiService
import com.anggit97.data.db.MovieeeDatabase
import com.anggit97.data.repository.internal.AccountRepositoryImpl
import com.anggit97.data.repository.internal.AuthRepositoryImpl
import com.anggit97.data.repository.internal.MovieRepositoryImpl
import com.anggit97.data.repository.internal.mapper.auth.RequestTokenMapper
import com.anggit97.model.repository.AccountRepository
import com.anggit97.model.repository.AuthRepository
import com.anggit97.model.repository.MovieRepository
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
    fun provideMovieRepository(
        movieDatabase: MovieeeDatabase,
        movieApiService: MovieApiService,
    ): MovieRepository {
        return MovieRepositoryImpl(
            local = movieDatabase,
            movieApiService = movieApiService,
        )
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authApiService: AuthApiService,
        sessionManagerStore: SessionManagerStore,
        requestTokenMapper: RequestTokenMapper
    ): AuthRepository {
        return AuthRepositoryImpl(
            authApiService,
            sessionManagerStore,
            requestTokenMapper
        )
    }

    @Provides
    @Singleton
    fun provideAccountRepository(
        accountApiService: AccountApiService,
        sessionManagerStore: SessionManagerStore
    ): AccountRepository {
        return AccountRepositoryImpl(
            accountApiService,
            sessionManagerStore
        )
    }
}