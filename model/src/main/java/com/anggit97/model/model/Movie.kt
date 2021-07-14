package com.anggit97.model.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * Created by Anggit Prayogo on 03,July,2021
 * GitHub : https://github.com/anggit97
 */
@Parcelize
data class  Movie(
    val id: Long = 0,
    val adult: Boolean,
    val backdropPath: String,
    val genres: List<String>? = emptyList(),
    val movieId: Long,
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
) : Parcelable{


    fun getPosterUrl(): String{
        return "https://image.tmdb.org/t/p/w500$posterPath"
    }

    fun getBackdropUrl(): String{
        return "https://image.tmdb.org/t/p/w500$backdropPath"
    }

    fun getHumanFriendlyReleaseDate(): String{
        return releaseDate
    }

    fun getAge(): String{
        return if(adult){
            "Dewasa"
        }else{
            "Semua Usia"
        }
    }
}