package com.anggit97.data.db.internal.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Created by Anggit Prayogo on 13,July,2021
 * GitHub : https://github.com/anggit97
 */
@Entity(tableName = "remote_keys_movie_plan")
data class MoviePlanRemoteKeys(
    @PrimaryKey val movieId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)