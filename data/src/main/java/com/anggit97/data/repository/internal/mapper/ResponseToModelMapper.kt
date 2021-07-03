package com.anggit97.data.repository.internal.mapper

import com.anggit97.data.api.response.MovieListResponse
import com.anggit97.data.api.response.MovieResponse
import com.anggit97.model.Movie


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