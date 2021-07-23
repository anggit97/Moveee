package com.anggit97.model.domain.moviefavourite

import androidx.paging.PagingData
import com.anggit97.model.model.Movie
import com.anggit97.model.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieFavouriteUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : MovieFavouriteUseCase {

    override fun getFavoriteMovieList(): Flow<PagingData<Movie>> {
        return repository.getFavoriteMovieList()
    }

    override suspend fun addFavoriteMovie(movie: Movie) {
        return repository.addFavoriteMovie(movie)
    }

    override suspend fun removeFavoriteMovie(movieId: Long) {
        return repository.removeFavoriteMovie(movieId)
    }

    override suspend fun isFavoriteMovie(movieId: Long): Boolean {
        return repository.isFavoriteMovie(movieId)
    }
}