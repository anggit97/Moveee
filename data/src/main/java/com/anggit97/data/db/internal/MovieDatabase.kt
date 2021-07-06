package com.anggit97.data.db.internal

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anggit97.data.db.internal.converter.FavouriteMovieTypeConverters
import com.anggit97.data.db.internal.dao.FavouriteMovieDao
import com.anggit97.data.db.internal.entity.FavouriteMovieEntity


/**
 * Created by Anggit Prayogo on 06,July,2021
 * GitHub : https://github.com/anggit97
 */
@Database(
    entities = [
        FavouriteMovieEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(FavouriteMovieTypeConverters::class)
internal abstract class MovieDatabase: RoomDatabase() {

    abstract fun favouriteMovieDao(): FavouriteMovieDao
}