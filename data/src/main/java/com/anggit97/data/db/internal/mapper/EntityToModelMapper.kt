package com.anggit97.data.db.internal.mapper

import com.anggit97.data.db.internal.entity.FavouriteMovieEntity
import com.anggit97.data.db.internal.entity.MovieEntity
import com.anggit97.model.Movie


/**
 * Created by Anggit Prayogo on 03,July,2021
 * GitHub : https://github.com/anggit97
 */
internal fun MovieEntity.toMovie() = Movie(
    adult ?: false,
    backdropPath ?: "",
    genres,
    id,
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

internal fun FavouriteMovieEntity.toMovie() = Movie(
    adult,
    backdropPath ?: "",
    genres,
    id,
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