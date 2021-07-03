package com.anggit97.data.db.internal.entity

import kotlinx.serialization.Serializable

@Serializable
data class MovieEntity(
    val adult: Boolean?,
    val backdropPath: String?,
    val genres: List<String>? = emptyList(),
    val id: Int,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?
)
