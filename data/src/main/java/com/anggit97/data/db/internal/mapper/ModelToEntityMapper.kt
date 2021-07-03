package com.anggit97.data.db.internal.mapper

import com.anggit97.data.db.internal.entity.MovieEntity
import com.anggit97.model.Movie


/**
 * Created by Anggit Prayogo on 03,July,2021
 * GitHub : https://github.com/anggit97
 */
internal fun Movie.toMovieEntity(): MovieEntity {
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