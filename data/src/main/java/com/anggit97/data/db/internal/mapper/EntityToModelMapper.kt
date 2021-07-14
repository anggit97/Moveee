package com.anggit97.data.db.internal.mapper

import com.anggit97.data.db.internal.entity.FavouriteMovieEntity
import com.anggit97.data.db.internal.entity.MovieEntity
import com.anggit97.model.model.Movie


/**
 * Created by Anggit Prayogo on 03,July,2021
 * GitHub : https://github.com/anggit97
 */
fun MovieEntity.toMovie() = Movie(
    id,
    adult ?: false,
    backdropPath ?: "",
    genres,
    movieId,
    originalLanguage,
    originalTitle ?: "-",
    overview ?: "-",
    popularity,
    posterPath ?: "",
    releaseDate ?: "",
    title ?: "-",
    video ?: false,
    voteAverage ?: 0.0,
    voteCount ?: 0
)

fun FavouriteMovieEntity.toMovie() = Movie(
    id.toLong(),
    adult,
    backdropPath ?: "",
    genres,
    id.toLong(),
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    posterPath,
    releaseDate,
    title,
    video,
    voteAverage,
    voteCount
)