package com.anggit97.model.domain.moviesearch

import androidx.paging.PagingData
import com.anggit97.model.model.Movie
import com.anggit97.model.repository.MovieeeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieSearchUseCaseImpl @Inject constructor(
    private val repository: MovieeeRepository
) : MovieSearchUseCase {

    override suspend fun searchMovie(query: String): Flow<PagingData<Movie>> {
        return repository.searchMovie(query)
    }
}