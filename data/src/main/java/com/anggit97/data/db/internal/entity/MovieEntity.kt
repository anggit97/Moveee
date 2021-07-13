package com.anggit97.data.db.internal.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "cached_movie_list")
@Serializable
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "adult")
    val adult: Boolean?,
    @ColumnInfo(name = "backdropPath")
    val backdropPath: String?,
    @ColumnInfo(name = "genres")
    val genres: List<String>? = emptyList(),
    @ColumnInfo(name = "movieId")
    val movieId: Long,
    @ColumnInfo(name = "originalLanguage")
    val originalLanguage: String?,
    @ColumnInfo(name = "originalTitle")
    val originalTitle: String?,
    @ColumnInfo(name = "overview")
    val overview: String?,
    @ColumnInfo(name = "popularity")
    val popularity: Double?,
    @ColumnInfo(name ="posterPath")
    val posterPath: String?,
    @ColumnInfo(name = "releaseDate")
    val releaseDate: String?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "video")
    val video: Boolean?,
    @ColumnInfo(name = "voteAverage")
    val voteAverage: Double?,
    @ColumnInfo(name = "voteCount")
    val voteCount: Int?,
    @ColumnInfo(name = "type")
    val type: String
) {

    companion object {
        const val TYPE_NOW = "type_now"
        const val TYPE_UPCOMING = "type_upcoming"
        const val TYPE_DISCOVER = "type_discover"
        const val TYPE_POPULAR = "type_popular"
        const val TYPE_TOP_RATED = "type_top_rated"
    }
}
