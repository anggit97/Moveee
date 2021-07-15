package com.anggit97.data.api

import com.anggit97.data.api.response.CreditsListResponse
import com.anggit97.data.api.response.MovieDetailResponse
import com.anggit97.data.api.response.MovieListResponse
import com.anggit97.data.api.response.MovieVideosResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by Anggit Prayogo on 02,July,2021
 * GitHub : https://github.com/anggit97
 */
interface MovieeeApiService {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovieList(@Query("page") page: String): MovieListResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovieList(@Query("page") page: String): MovieListResponse

    @GET("discover/movie")
    suspend fun getDiscoverMovieList(): MovieListResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovieList(): MovieListResponse

    @GET("movie/{id}")
    suspend fun getMovieById(@Path("id") id: String): MovieDetailResponse

    @GET("movie/{id}/videos")
    suspend fun getMovieVideos(@Path("id") id: String): MovieVideosResponse

    @GET("movie/{id}/credits")
    suspend fun getMovieCredits(@Path("id") id: String): CreditsListResponse

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("page") page: String
    ): MovieListResponse

    @GET("movie/latest")
    suspend fun getLatestMovie(): MovieDetailResponse
}