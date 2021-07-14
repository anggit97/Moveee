package com.anggit97.model.domain.moviedetail

import com.anggit97.model.model.MovieDetail


/**
 * Created by Anggit Prayogo on 14,July,2021
 * GitHub : https://github.com/anggit97
 */
interface MovieDetailUseCase {
    suspend fun getMovieById(id: String): MovieDetail
}