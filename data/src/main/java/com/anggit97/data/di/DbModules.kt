package com.anggit97.data.di

import android.content.Context
import androidx.room.Room
import com.anggit97.data.db.MovieeeDatabase
import com.anggit97.data.db.internal.MovieCacheDatabase
import com.anggit97.data.db.internal.MovieDatabase
import com.anggit97.data.db.internal.MovieeeDatabaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Anggit Prayogo on 02,July,2021
 * GitHub : https://github.com/anggit97
 */
@Module
@InstallIn(SingletonComponent::class)
object DbModules {

    @Singleton
    @Provides
    fun provideMoveeeDatabase(
        @ApplicationContext context: Context
    ): MovieeeDatabase {
        val movieCacheDb = createMovieCacheDatabase(context)
        val movieeeDb = createMovieeeDatabase(context)
        return MovieeeDatabaseImpl(
            movieCacheDb,
            movieeeDb
        )
    }

    private fun createMovieeeDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            "movieee.db"
        ).build()
    }

    private fun createMovieCacheDatabase(context: Context): MovieCacheDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MovieCacheDatabase::class.java,
            "movie_cache.db"
        ).build()
    }
}