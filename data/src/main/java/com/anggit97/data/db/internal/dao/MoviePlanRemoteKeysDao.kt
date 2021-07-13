package com.anggit97.data.db.internal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anggit97.data.db.internal.entity.MovieNowRemoteKeys
import com.anggit97.data.db.internal.entity.MoviePlanRemoteKeys


/**
 * Created by Anggit Prayogo on 12,July,2021
 * GitHub : https://github.com/anggit97
 */
@Dao
interface MoviePlanRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movieNowRemoteKey: List<MoviePlanRemoteKeys>)

    @Query("SELECT * FROM remote_keys_movie_plan WHERE movieId=:movieId")
    suspend fun remoteKeysMovieId(movieId: Long): MoviePlanRemoteKeys?

    @Query("SELECT * FROM remote_keys_movie_plan ")
    suspend fun allRemoteKeys(): List<MoviePlanRemoteKeys?>

    @Query("DELETE FROM remote_keys_movie_plan")
    suspend fun clearRemoteKeys()
}