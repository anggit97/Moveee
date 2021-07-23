package com.anggit97.model.di

import com.anggit97.model.domain.account.AccountUseCase
import com.anggit97.model.domain.account.AccountUseCaseImpl
import com.anggit97.model.domain.auth.AuthUseCase
import com.anggit97.model.domain.auth.AuthUseCaseImpl
import com.anggit97.model.domain.moviedetail.MovieDetailUseCase
import com.anggit97.model.domain.moviedetail.MovieDetailUseCaseImpl
import com.anggit97.model.domain.moviefavourite.MovieFavouriteUseCase
import com.anggit97.model.domain.moviefavourite.MovieFavouriteUseCaseImpl
import com.anggit97.model.domain.movielist.MovieListUseCase
import com.anggit97.model.domain.movielist.MovieListUseCaseImpl
import com.anggit97.model.domain.moviesearch.MovieSearchUseCase
import com.anggit97.model.domain.moviesearch.MovieSearchUseCaseImpl
import com.anggit97.model.domain.moviesreminder.MoviesReminderUseCase
import com.anggit97.model.domain.moviesreminder.MoviesReminderUseCaseImpl
import com.anggit97.model.repository.AccountRepository
import com.anggit97.model.repository.AuthRepository
import com.anggit97.model.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Anggit Prayogo on 14,July,2021
 * GitHub : https://github.com/anggit97
 */
@InstallIn(SingletonComponent::class)
@Module
class DomainModule {

    @Provides
    @Singleton
    fun provideMovieListUseCase(movieRepository: MovieRepository): MovieListUseCase =
        MovieListUseCaseImpl(movieRepository)

    @Provides
    @Singleton
    fun provideMovieDetailUseCase(movieRepository: MovieRepository): MovieDetailUseCase =
        MovieDetailUseCaseImpl(movieRepository)

    @Provides
    @Singleton
    fun provideMovieFavouriteUseCase(movieRepository: MovieRepository): MovieFavouriteUseCase =
        MovieFavouriteUseCaseImpl(movieRepository)

    @Provides
    @Singleton
    fun provideMovieSearchUseCase(movieRepository: MovieRepository): MovieSearchUseCase =
        MovieSearchUseCaseImpl(movieRepository)

    @Provides
    @Singleton
    fun provideMovieReminderUseCase(movieRepository: MovieRepository): MoviesReminderUseCase =
        MoviesReminderUseCaseImpl(movieRepository)

    @Provides
    @Singleton
    fun provideAuthUseCase(authRepository: AuthRepository): AuthUseCase =
        AuthUseCaseImpl(authRepository)

    @Provides
    @Singleton
    fun provideAccountUseCase(accountRepository: AccountRepository): AccountUseCase =
        AccountUseCaseImpl(accountRepository)
}