package com.anggit97.data.repository.internal.mapper

import com.anggit97.data.api.response.MovieListResponse
import com.anggit97.data.api.response.MovieResponse
import com.anggit97.data.db.internal.entity.MovieEntity
import com.anggit97.data.db.internal.entity.MovieListEntity


/**
 * Created by Anggit Prayogo on 12,July,2021
 * GitHub : https://github.com/anggit97
 */
fun MovieListResponse.toMovieListEntity(type: String) =
    MovieListEntity(type, movies?.toListMovieEntity() ?: emptyList())

fun List<MovieResponse>.toListMovieEntity() = map {
    MovieEntity(
        it.adult,
        it.backdropPath,
        it.genreIds?.map { genre -> genre.toString() },
        it.id ?: 0,
        it.originalLanguage,
        it.originalTitle,
        it.overview,
        it.popularity,
        it.posterPath,
        it.releaseDate,
        it.title,
        it.video,
        it.voteAverage,
        it.voteCount
    )
} ?: emptyList()