package com.anggit97.model.domain.movielist

import androidx.paging.PagingData
import com.anggit97.model.model.Movie
import kotlinx.coroutines.flow.Flow


/**
 * Created by Anggit Prayogo on 14,July,2021
 * GitHub : https://github.com/anggit97
 */
interface MovieListUseCase {
    fun getNowMovieListPaging(): Flow<PagingData<Movie>>
    fun getPlanMovieList(): Flow<PagingData<Movie>>
}