package com.anggit97.data.db.internal

import com.anggit97.data.api.response.MovieListResponse
import com.anggit97.data.api.response.MovieResponse
import com.anggit97.model.model.Movie


/**
 * Created by Anggit Prayogo on 15,July,2021
 * GitHub : https://github.com/anggit97
 */
object MovieDataFactories {
    val movieListResponse: MovieListResponse by lazy {
        MovieListResponse(
            datesResponse = null,
            page = null,
            movies = listOf(movieResponse1, movieResponse2),
            totalPages = null,
            totalResults = null
        )
    }


    private val movieResponse1 = MovieResponse(
        adult = null,
        backdropPath = null,
        genreIds = listOf(),
        id = 1,
        originalLanguage = null,
        originalTitle = null,
        overview = null,
        popularity = null,
        posterPath = null,
        releaseDate = null,
        title = null,
        video = null,
        voteAverage = null,
        voteCount = null
    )

    private val movieResponse2 = MovieResponse(
        adult = null,
        backdropPath = null,
        genreIds = listOf(),
        id = 2,
        originalLanguage = null,
        originalTitle = null,
        overview = null,
        popularity = null,
        posterPath = null,
        releaseDate = null,
        title = null,
        video = null,
        voteAverage = null,
        voteCount = null
    )


    val movie1 = Movie(
        id = 0,
        adult = false,
        backdropPath = "",
        genres = listOf(),
        movieId = 1,
        originalLanguage = null,
        originalTitle = "",
        overview = "",
        popularity = null,
        posterPath = "",
        releaseDate = "",
        title = "",
        video = false,
        voteAverage = 0.0,
        voteCount = 0
    )

    val movie2 = Movie(
        id = 0,
        adult = false,
        backdropPath = "",
        genres = listOf(),
        movieId = 2,
        originalLanguage = null,
        originalTitle = "",
        overview = "",
        popularity = null,
        posterPath = "",
        releaseDate = "",
        title = "",
        video = false,
        voteAverage = 0.0,
        voteCount = 0
    )
}