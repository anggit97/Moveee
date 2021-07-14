package com.anggit97.model.repository

import androidx.paging.PagingData
import com.anggit97.model.Cast
import com.anggit97.model.Movie
import com.anggit97.model.MovieDetail
import com.anggit97.model.Video
import kotlinx.coroutines.flow.Flow


/**
 * Created by Anggit Prayogo on 03,July,2021
 * GitHub : https://github.com/anggit97
 */
interface MovieeeRepository {


    fun getNowMovieList(): Flow<List<Movie>>
    suspend fun updateNowMovieList()
    suspend fun updateAndGetNowMovieList(): List<Movie>
    fun getNowMovieListPaging(): Flow<PagingData<Movie>>

    fun getPlanMovieList(): Flow<PagingData<Movie>>
    suspend fun updatePlanMovieList()
    suspend fun updateAndGetPlanMovieList(): List<Movie>

    suspend fun getMovieById(id: String): MovieDetail

    fun getFavoriteMovieList(): Flow<PagingData<Movie>>
    suspend fun addFavoriteMovie(movie: Movie)
    suspend fun removeFavoriteMovie(movieId: Long)
    suspend fun isFavoriteMovie(movieId: Long): Boolean

    suspend fun searchMovie(query: String): Flow<PagingData<Movie>>

    suspend fun getMovieVideos(id: String): List<Video>

    suspend fun getMovieCredits(id: String): List<Cast>
}