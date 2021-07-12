package com.anggit97.data.repository.internal.mapper

import com.anggit97.data.api.response.MovieListResponse
import com.anggit97.data.api.response.MovieResponse
import com.anggit97.data.db.internal.entity.MovieEntity


/**
 * Created by Anggit Prayogo on 12,July,2021
 * GitHub : https://github.com/anggit97
 */
fun MovieListResponse.toMovieListEntity(type: String): List<MovieEntity> =
    this.movies?.map { it.toMovieEntity(type) } ?: emptyList()

fun MovieResponse.toMovieEntity(type: String): MovieEntity = MovieEntity(
    adult,
    backdropPath,
    genreIds?.map { genre -> genre.toString() },
    id ?: 0,
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    posterPath,
    releaseDate,
    title,
    video,
    voteAverage,
    voteCount,
    type
)