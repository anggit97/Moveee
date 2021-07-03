package com.anggit97.model.repository

import com.anggit97.model.Movie
import kotlinx.coroutines.flow.Flow


/**
 * Created by Anggit Prayogo on 03,July,2021
 * GitHub : https://github.com/anggit97
 */
interface MovieeeRepository {

    fun getNowMovieList(): Flow<List<Movie>>
    suspend fun updateNowMovieList()
    suspend fun updateAndGetNowMovieList(): List<Movie>
}