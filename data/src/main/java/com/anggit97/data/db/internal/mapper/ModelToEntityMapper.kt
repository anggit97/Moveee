package com.anggit97.data.db.internal.mapper

import com.anggit97.data.db.internal.entity.FavouriteMovieEntity
import com.anggit97.data.db.internal.entity.MovieEntity
import com.anggit97.model.Movie


private val Movie.movieEntity: MovieEntity
    get() = MovieEntity(
        adult,
        backdropPath,
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

/**
 * Created by Anggit Prayogo on 03,July,2021
 * GitHub : https://github.com/anggit97
 */
fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        adult,
        backdropPath,
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
}

internal fun Movie.toFavouriteEntity(): FavouriteMovieEntity {
    return FavouriteMovieEntity(
        id,
        adult,
        backdropPath,
        genres,
        originalLanguage ?: "",
        originalTitle,
        overview,
        popularity ?: 0.0,
        posterPath,
        releaseDate,
        title,
        video,
        voteAverage,
        voteCount
    )
}