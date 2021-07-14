package com.anggit97.model.domain.moviesearch

import androidx.paging.PagingData
import com.anggit97.model.model.Movie
import kotlinx.coroutines.flow.Flow


/**
 * Created by Anggit Prayogo on 14,July,2021
 * GitHub : https://github.com/anggit97
 */
interface MovieSearchUseCase {
    suspend fun searchMovie(query: String): Flow<PagingData<Movie>>
}
