package com.anggit97.data.api

import com.anggit97.data.api.response.MovieDetailResponse
import com.anggit97.data.api.response.MovieListResponse
import com.anggit97.data.api.response.MovieVideosResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Created by Anggit Prayogo on 02,July,2021
 * GitHub : https://github.com/anggit97
 */
interface MovieeeApiService {

    @GET("movie/now_playing?api_key=00fadd6af89412de4c1a3ecd7fe631f6&language=id")
    suspend fun getNowPlayingMovieList(): MovieListResponse

    @GET("discover/movie?api_key=00fadd6af89412de4c1a3ecd7fe631f6&language=id")
    suspend fun getDiscoverMovieList(): MovieListResponse

    @GET("movie/popular?api_key=00fadd6af89412de4c1a3ecd7fe631f6&language=id")
    suspend fun getPopularMovieList(): MovieListResponse

    @GET("movie/top_rated?api_key=00fadd6af89412de4c1a3ecd7fe631f6&language=id")
    suspend fun getTopRatedMovieList(): MovieListResponse

    @GET("movie/{id}?api_key=00fadd6af89412de4c1a3ecd7fe631f6&language=id")
    suspend fun getMovieById(@Path("id") id: String): MovieDetailResponse

    @GET("movie/{id}/videos?api_key=00fadd6af89412de4c1a3ecd7fe631f6")
    suspend fun getMovieVideos(@Path("id") id: String): MovieVideosResponse
}