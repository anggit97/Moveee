package com.anggit97.model.domain.movielist

import androidx.paging.PagingData
import com.anggit97.model.model.Movie
import com.anggit97.model.repository.MovieeeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieListUseCaseImpl @Inject constructor(
    private val movieeeRepository: MovieeeRepository
) : MovieListUseCase {

    override fun getNowMovieListPaging(): Flow<PagingData<Movie>> {
        return movieeeRepository.getNowMovieListPaging()
    }

    override fun getPlanMovieList(): Flow<PagingData<Movie>> {
        return movieeeRepository.getPlanMovieList()
    }
}