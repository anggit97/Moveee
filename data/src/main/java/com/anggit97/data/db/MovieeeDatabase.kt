package com.anggit97.data.db

import androidx.paging.PagingSource
import com.anggit97.data.db.internal.MovieCacheDatabase
import com.anggit97.data.db.internal.MovieDatabase
import com.anggit97.data.db.internal.entity.FavouriteMovieEntity
import com.anggit97.data.db.internal.entity.MovieEntity
import com.anggit97.model.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Created by Anggit Prayogo on 02,July,2021
 * GitHub : https://github.com/anggit97
 */
interface MovieeeDatabase {

    fun getMovieCacheDatabase(): MovieCacheDatabase
    fun getMovieeeDatabase(): MovieDatabase

    suspend fun saveNowMovieList(movieList: List<Movie>)
    fun getNowMovieListFlow(): Flow<List<Movie>>
    suspend fun getNowMovieList(): List<Movie>

    fun getNowMovieListNowPaging(): PagingSource<Int, MovieEntity>

    suspend fun savePlanMovieList(movieList: List<Movie>)
    fun getPlanMovieListFlow(): PagingSource<Int, MovieEntity>
    suspend fun getPlanMovieList(): List<Movie>

    suspend fun getAllMovieList(): List<Movie>

    suspend fun addFavoriteMovie(movie: Movie)
    suspend fun removeFavoriteMovie(movieId: Long)
    fun getFavoriteMovieList(): PagingSource<Int, FavouriteMovieEntity>
    suspend fun isFavoriteMovie(movieId: Long): Boolean
}