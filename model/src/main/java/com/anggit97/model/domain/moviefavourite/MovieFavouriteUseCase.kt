package com.anggit97.model.domain.moviefavourite

import androidx.paging.PagingData
import com.anggit97.model.model.Movie
import kotlinx.coroutines.flow.Flow


/**
 * Created by Anggit Prayogo on 14,July,2021
 * GitHub : https://github.com/anggit97
 */
interface MovieFavouriteUseCase {
    fun getFavoriteMovieList(): Flow<PagingData<Movie>>
    suspend fun addFavoriteMovie(movie: Movie)
    suspend fun removeFavoriteMovie(movieId: Long)
    suspend fun isFavoriteMovie(movieId: Long): Boolean
}
