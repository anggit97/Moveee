package com.anggit97.data.db.internal.dao

import androidx.room.*
import com.anggit97.data.db.internal.entity.MovieListEntity
import kotlinx.coroutines.flow.Flow


/**
 * Created by Anggit Prayogo on 02,July,2021
 * GitHub : https://github.com/anggit97
 */
@Dao
internal interface MovieCacheDao {

    @Query("select * from cached_movie_list where type like :type")
    fun getMovieListByType(type: String): Flow<MovieListEntity>

    @Query("select * from cached_movie_list where type like :type")
    suspend fun findByType(type: String): MovieListEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(response: MovieListEntity)

    @Delete
    suspend fun delete(response: MovieListEntity)

    @Query("DELETE FROM cached_movie_list")
    suspend fun deleteAll()
}