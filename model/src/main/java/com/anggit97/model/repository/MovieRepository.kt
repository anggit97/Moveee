package com.anggit97.model.repository

import androidx.paging.PagingData
import com.anggit97.model.model.*
import kotlinx.coroutines.flow.Flow


/**
 * Created by Anggit Prayogo on 03,July,2021
 * GitHub : https://github.com/anggit97
 */
interface MovieRepository {

    fun getNowMovieListPaging(): Flow<PagingData<Movie>>
    fun getPlanMovieList(): Flow<PagingData<Movie>>

    fun getFavoriteMovieList(): Flow<PagingData<Movie>>
    suspend fun addFavoriteMovie(movie: Movie)
    suspend fun removeFavoriteMovie(movieId: Long)
    suspend fun isFavoriteMovie(movieId: Long): Boolean

    suspend fun searchMovie(query: String): Flow<PagingData<Movie>>

    suspend fun getMovieById(id: String): MovieDetail
    suspend fun getMovieVideos(id: String): List<Video>
    suspend fun getMovieCredits(id: String): List<Cast>

    suspend fun getLatestMovie(): MovieDetail
}