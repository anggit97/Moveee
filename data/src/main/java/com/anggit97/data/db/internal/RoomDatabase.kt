package com.anggit97.data.db.internal

import com.anggit97.data.db.MoveeeDatabase
import com.anggit97.data.db.internal.dao.MovieCacheDao
import com.anggit97.data.db.internal.entity.MovieListEntity
import com.anggit97.data.db.internal.entity.MovieListEntity.Companion.TYPE_NOW
import com.anggit97.data.db.internal.entity.MovieListEntity.Companion.TYPE_POPULAR
import com.anggit97.data.db.internal.mapper.toMovie
import com.anggit97.data.db.internal.mapper.toMovieEntity
import com.anggit97.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map


/**
 * Created by Anggit Prayogo on 02,July,2021
 * GitHub : https://github.com/anggit97
 */
internal class RoomDatabase(
    private val cacheMovieCacheDao: MovieCacheDao
) : MoveeeDatabase {

    override suspend fun saveNowMovieList(movieList: List<Movie>) {
        saveMovieListAs(TYPE_NOW, movieList)
    }

    private suspend fun saveMovieListAs(type: String, movieList: List<Movie>) {
        cacheMovieCacheDao.insert(
            MovieListEntity(
                type,
                movieList.map { it.toMovieEntity() }
            )
        )
    }

    override fun getNowMovieListFlow(): Flow<List<Movie>> {
        return getMovieList(TYPE_NOW)
    }

    private fun getMovieList(type: String): Flow<List<Movie>> {
        return cacheMovieCacheDao.getMovieListByType(type)
            .map { it -> it.list.map { it.toMovie() } }
            .catch { emit(emptyList()) }
    }


    override suspend fun savePlanMovieList(movieList: List<Movie>) {
        saveMovieListAs(TYPE_POPULAR, movieList)
    }

    override fun getPlanMovieListFlow(): Flow<List<Movie>> {
        return getMovieList(TYPE_POPULAR)
    }
}