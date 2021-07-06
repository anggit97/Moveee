package com.anggit97.data.db

import com.anggit97.model.Movie
import kotlinx.coroutines.flow.Flow


/**
 * Created by Anggit Prayogo on 02,July,2021
 * GitHub : https://github.com/anggit97
 */
interface MoveeeDatabase {

    suspend fun saveNowMovieList(movieList: List<Movie>)
    fun getNowMovieListFlow(): Flow<List<Movie>>
    suspend fun getNowMovieList(): List<Movie>

    suspend fun savePlanMovieList(movieList: List<Movie>)
    fun getPlanMovieListFlow(): Flow<List<Movie>>
    suspend fun getPlanMovieList(): List<Movie>

    suspend fun getAllMovieList(): List<Movie>

    suspend fun addFavoriteMovie(movie: Movie)
    suspend fun removeFavoriteMovie(movieId: Int)
    fun getFavoriteMovieList(): Flow<List<Movie>>
    suspend fun isFavoriteMovie(movieId: Int): Boolean
}