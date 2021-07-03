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
}