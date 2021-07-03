package com.anggit97.data.api

import com.anggit97.data.api.response.MovieListResponse
import retrofit2.http.GET


/**
 * Created by Anggit Prayogo on 02,July,2021
 * GitHub : https://github.com/anggit97
 */
interface MovieeeApiService {

    @GET("movie/now_playing?api_key=00fadd6af89412de4c1a3ecd7fe631f6&language=en-US")
    suspend fun getNowPlayingMovieList(): MovieListResponse

    @GET("discover/movie?api_key=00fadd6af89412de4c1a3ecd7fe631f6&language=en-US")
    suspend fun getDiscoverMovieList(): MovieListResponse

    @GET("movie/popular?api_key=00fadd6af89412de4c1a3ecd7fe631f6&language=en-US")
    suspend fun getPopularMovieList(): MovieListResponse

    @GET("movie/top_rated?api_key=00fadd6af89412de4c1a3ecd7fe631f6&language=en-US")
    suspend fun getTopRatedMovieList(): MovieListResponse
}