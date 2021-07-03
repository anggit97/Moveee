package com.anggit97.data.db.internal

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anggit97.data.db.internal.converter.CacheDatabaseTypeConverter
import com.anggit97.data.db.internal.dao.MovieCacheDao
import com.anggit97.data.db.internal.entity.MovieListEntity


/**
 * Created by Anggit Prayogo on 02,July,2021
 * GitHub : https://github.com/anggit97
 */
@Database(entities = [MovieListEntity::class], version = 1, exportSchema = false)
@TypeConverters(CacheDatabaseTypeConverter::class)
internal abstract class MovieCacheDatabase : RoomDatabase() {

    abstract fun movieCacheDao(): MovieCacheDao
}