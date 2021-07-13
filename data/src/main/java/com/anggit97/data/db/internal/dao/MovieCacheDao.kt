package com.anggit97.data.db.internal.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.anggit97.data.db.internal.entity.MovieEntity
import kotlinx.coroutines.flow.Flow


/**
 * Created by Anggit Prayogo on 02,July,2021
 * GitHub : https://github.com/anggit97
 */
@Dao
interface MovieCacheDao {

    @Query("select * from cached_movie_list where type like :type")
    fun getMovieListByType(type: String): Flow<List<MovieEntity>>

    @Query("select * from cached_movie_list where type like :type")
    fun getMovieListByTypePaging(type: String): PagingSource<Int, MovieEntity>

    @Query("select * from cached_movie_list where type like :type")
    suspend fun findByType(type: String): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(response: List<MovieEntity>)

    @Delete
    suspend fun delete(response: MovieEntity)

    @Query("DELETE FROM cached_movie_list")
    suspend fun deleteAll()
}