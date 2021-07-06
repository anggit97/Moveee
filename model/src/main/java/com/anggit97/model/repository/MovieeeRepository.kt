package com.anggit97.model.repository

import com.anggit97.model.Movie
import com.anggit97.model.MovieDetail
import kotlinx.coroutines.flow.Flow


/**
 * Created by Anggit Prayogo on 03,July,2021
 * GitHub : https://github.com/anggit97
 */
interface MovieeeRepository {

    fun getNowMovieList(): Flow<List<Movie>>
    suspend fun updateNowMovieList()
    suspend fun updateAndGetNowMovieList(): List<Movie>


    fun getPlanMovieList(): Flow<List<Movie>>
    suspend fun updatePlanMovieList()
    suspend fun updateAndGetPlanMovieList(): List<Movie>

    suspend fun getMovieById(id: String): MovieDetail

    fun getFavoriteMovieList(): Flow<List<Movie>>
    suspend fun addFavoriteMovie(movie: Movie)
    suspend fun removeFavoriteMovie(movieId: Int)
    suspend fun isFavoriteMovie(movieId: Int): Boolean
}