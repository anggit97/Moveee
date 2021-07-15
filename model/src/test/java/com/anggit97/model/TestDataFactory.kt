package com.anggit97.model

import androidx.paging.PagingData
import com.anggit97.model.model.Cast
import com.anggit97.model.model.Movie
import com.anggit97.model.model.MovieDetail
import com.anggit97.model.model.Video
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



    //MovieDetail
    val movieDetail by lazy {
        MovieDetail(
        adult = null,
        backdrop_path = null,
        budget = null,
        genres = listOf(),
        homepage = null,
        id = null,
        imdb_id = null,
        original_language = null,
        original_title = null,
        overview = null,
        popularity = null,
        poster_path = null,
        production_companies = listOf(),
        production_countries = listOf(),
        release_date = null,
        revenue = null,
        runtime = null,
        spoken_languages = listOf(),
        status = null,
        tagline = null,
        title = "naruto",
        video = null,
        vote_average = null,
        vote_count = null,
        videos = videoList,
        casts = castList
    )
    }

    val videoList = listOf(
        Video(
            id = "",
            iso_3166_1 = "",
            iso_639_1 = "",
            key = "",
            name = "video1",
            site = "",
            size = 0,
            type = ""
        )
    )

    val castList = listOf(
        Cast(
            adult = null,
            cast_id = null,
            character = null,
            credit_id = null,
            gender = null,
            id = null,
            known_for_department = null,
            name = "cast1",
            order = null,
            original_name = null,
            popularity = null,
            profile_path = null
        )
    )
}