package com.anggit97.data.db.internal

import com.anggit97.data.db.MoveeeDatabase
import com.anggit97.data.db.internal.dao.MovieCacheDao


/**
 * Created by Anggit Prayogo on 02,July,2021
 * GitHub : https://github.com/anggit97
 */
internal class RoomDatabase(
    private val cacheMovieCacheDao: MovieCacheDao
) : MoveeeDatabase {

}