package com.anggit97.model.di

import com.anggit97.model.domain.moviedetail.MovieDetailUseCase
import com.anggit97.model.domain.moviedetail.MovieDetailUseCaseImpl
import com.anggit97.model.domain.moviefavourite.MovieFavouriteUseCase
import com.anggit97.model.domain.moviefavourite.MovieFavouriteUseCaseImpl
import com.anggit97.model.domain.movielist.MovieListUseCase
import com.anggit97.model.domain.movielist.MovieListUseCaseImpl
import com.anggit97.model.domain.moviesearch.MovieSearchUseCase
import com.anggit97.model.domain.moviesearch.MovieSearchUseCaseImpl
import com.anggit97.model.repository.MovieeeRepository
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
    fun provideMovieListUseCase(movieeeRepository: MovieeeRepository): MovieListUseCase =
        MovieListUseCaseImpl(movieeeRepository)

    @Provides
    @Singleton
    fun provideMovieDetailUseCase(movieeeRepository: MovieeeRepository): MovieDetailUseCase =
        MovieDetailUseCaseImpl(movieeeRepository)

    @Provides
    @Singleton
    fun provideMovieFavouriteUseCase(movieeeRepository: MovieeeRepository): MovieFavouriteUseCase =
        MovieFavouriteUseCaseImpl(movieeeRepository)

    @Provides
    @Singleton
    fun provideMovieSearchUseCase(movieeeRepository: MovieeeRepository): MovieSearchUseCase =
        MovieSearchUseCaseImpl(movieeeRepository)
}