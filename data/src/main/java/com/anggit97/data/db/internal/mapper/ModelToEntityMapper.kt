package com.anggit97.data.db.internal.mapper

import com.anggit97.data.db.internal.entity.FavouriteMovieEntity
import com.anggit97.data.db.internal.entity.MovieEntity
import com.anggit97.model.Movie

/**
 * Created by Anggit Prayogo on 03,July,2021
 * GitHub : https://github.com/anggit97
 */
fun List<Movie>.toMovieEntityList(type: String): List<MovieEntity> = map {
    MovieEntity(
        it.adult,
        it.backdropPath,
        it.genres,
        it.id,
        it.originalLanguage,
        it.originalTitle,
        it.overview,
        it.popularity,
        it.posterPath,
        it.releaseDate,
        it.title,
        it.video,
        it.voteAverage,
        it.voteCount,
        type
    )
}

fun Movie.toMovieEntity(type: String): MovieEntity {
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
        voteCount,
        type
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