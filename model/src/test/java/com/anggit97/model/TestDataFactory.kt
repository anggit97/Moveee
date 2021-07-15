package com.anggit97.model

import androidx.paging.PagingData
import com.anggit97.model.model.Movie
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow


/**
 * Created by Anggit Prayogo on 15,July,2021
 * GitHub : https://github.com/anggit97
 */
object TestDataFactory {

    val pagingDataMoviesFlow = flow { emit(pagingDataMovies) }

    private val pagingDataMovies by lazy { PagingData.from(listOf(movie1, movie2, movie3)) }

    val movie1 = Movie(
        id = 1,
        adult = false,
        backdropPath = "",
        genres = listOf(),
        movieId = 0,
        originalLanguage = null,
        originalTitle = "",
        overview = "",
        popularity = null,
        posterPath = "",
        releaseDate = "",
        title = "naruto",
        video = false,
        voteAverage = 0.0,
        voteCount = 0
    )

    val movie2 = Movie(
        id = 2,
        adult = false,
        backdropPath = "",
        genres = listOf(),
        movieId = 0,
        originalLanguage = null,
        originalTitle = "",
        overview = "",
        popularity = null,
        posterPath = "",
        releaseDate = "",
        title = "sasuke",
        video = false,
        voteAverage = 0.0,
        voteCount = 0
    )

    val movie3 = Movie(
        id = 3,
        adult = false,
        backdropPath = "",
        genres = listOf(),
        movieId = 0,
        originalLanguage = null,
        originalTitle = "",
        overview = "",
        popularity = null,
        posterPath = "",
        releaseDate = "",
        title = "itachi",
        video = false,
        voteAverage = 0.0,
        voteCount = 0
    )
}