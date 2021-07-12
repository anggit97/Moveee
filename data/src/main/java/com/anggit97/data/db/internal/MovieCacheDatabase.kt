package com.anggit97.data.db.internal

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anggit97.data.db.internal.converter.CacheDatabaseTypeConverter
import com.anggit97.data.db.internal.dao.MovieCacheDao
import com.anggit97.data.db.internal.dao.RemoteKeysDao
import com.anggit97.data.db.internal.entity.MovieEntity
import com.anggit97.data.db.internal.entity.RemoteKeys


/**
 * Created by Anggit Prayogo on 02,July,2021
 * GitHub : https://github.com/anggit97
 */
@Database(entities = [MovieEntity::class, RemoteKeys::class], version = 1, exportSchema = false)
@TypeConverters(CacheDatabaseTypeConverter::class)
abstract class MovieCacheDatabase : RoomDatabase() {

    abstract fun movieCacheDao(): MovieCacheDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}