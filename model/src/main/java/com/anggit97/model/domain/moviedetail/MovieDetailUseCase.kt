package com.anggit97.model.domain.moviedetail

import androidx.paging.PagingData
import com.anggit97.model.model.Cast
import com.anggit97.model.model.Movie
import com.anggit97.model.model.MovieDetail
import com.anggit97.model.model.Video
import kotlinx.coroutines.flow.Flow


/**
 * Created by Anggit Prayogo on 14,July,2021
 * GitHub : https://github.com/anggit97
 */
interface MovieDetailUseCase {
    suspend fun getMovieById(id: String): MovieDetail
    suspend fun getMovieVideos(id: String): List<Video>
    suspend fun getMovieCredits(id: String): List<Cast>
}