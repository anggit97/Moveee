package com.anggit97.data.repository.internal.mapper

import com.anggit97.data.api.response.*
import com.anggit97.model.Genre
import com.anggit97.model.Movie
import com.anggit97.model.MovieDetail


/**
 * Created by Anggit Prayogo on 03,July,2021
 * GitHub : https://github.com/anggit97
 */
internal fun MovieListResponse.toMovieList() = movies?.map { it.toMovie() } ?: emptyList()

private fun MovieResponse.toMovie() = Movie(
    adult ?: false,
    backdropPath ?: "",
    genreIds?.map { it.toString() }?.toList(),
    id ?: 0,
    originalLanguage,
    originalTitle ?: "",
    overview ?: "",
    popularity,
    posterPath ?: "",
    releaseDate ?: "",
    title ?: "",
    video ?: false,
    voteAverage ?: 0.0,
    voteCount ?: 0
)

internal fun MovieDetailResponse.toMovieDetail() = MovieDetail(
    adult,
    backdropPath,
    budget,
    listOf(Genre(0, "Avanture")),
    homepage,
    id,
    imdbId,
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    posterPath,
    emptyList(),
    emptyList(),
    releaseDate,
    revenue,
    runtime,
    emptyList(),
    status,
    tagline,
    title,
    video,
    voteAverage,
    voteCount,
)

internal fun GenreResponse.toGenre() = com.anggit97.model.Genre(
    id ?: 0,
    name ?: ""
)