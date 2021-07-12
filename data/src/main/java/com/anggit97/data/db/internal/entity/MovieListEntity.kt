package com.anggit97.data.db.internal.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Created by Anggit Prayogo on 02,July,2021
 * GitHub : https://github.com/anggit97
 */
@Entity(tableName = "cached_movie_list")
data class MovieListEntity(
    @PrimaryKey
    val type: String,
    val list: List<MovieEntity> = emptyList()
) {
    companion object {
        const val TYPE_NOW = "type_now"
        const val TYPE_UPCOMING = "type_upcoming"
        const val TYPE_DISCOVER = "type_discover"
        const val TYPE_POPULAR = "type_popular"
        const val TYPE_TOP_RATED = "type_top_rated"
    }
}