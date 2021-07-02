package com.anggit97.data.api

import com.anggit97.data.api.response.MovieListResponse
import retrofit2.http.GET


/**
 * Created by Anggit Prayogo on 02,July,2021
 * GitHub : https://github.com/anggit97
 */
interface MovieApiService {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovieList(): MovieListResponse

    @GET("discover/movie")
    suspend fun getDiscoverMovieList(): MovieListResponse

    @GET("movie/popular")
    suspend fun getPopularMovieList(): MovieListResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovieList(): MovieListResponse
}