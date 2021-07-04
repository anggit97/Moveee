package com.anggit97.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * Created by Anggit Prayogo on 03,July,2021
 * GitHub : https://github.com/anggit97
 */
@Parcelize
data class Movie(
    val adult: Boolean,
    val backdropPath: String,
    val genres: List<String>? = emptyList(),
    val id: Int,
    val originalLanguage: String?,
    val originalTitle: String,
    val overview: String,
    val popularity: Double?,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
) : Parcelable