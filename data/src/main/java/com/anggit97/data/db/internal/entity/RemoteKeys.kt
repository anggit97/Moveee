package com.anggit97.data.db.internal.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Created by Anggit Prayogo on 12,July,2021
 * GitHub : https://github.com/anggit97
 */
@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val movieId: String,
    val prevKey: Int?,
    val nextKey: Int?
)