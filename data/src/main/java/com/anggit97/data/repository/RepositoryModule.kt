package com.anggit97.data.repository

import com.anggit97.data.api.MovieeeApiService
import com.anggit97.data.db.MovieeeDatabase
import com.anggit97.data.repository.internal.MovieeeRepositoryImpl
import com.anggit97.model.repository.MovieeeRepository
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
        movieeeApiService: MovieeeApiService
    ): MovieeeRepository {
        return MovieeeRepositoryImpl(
            local = movieDatabase,
            remote = movieeeApiService,
        )
    }
}