package com.anggit97.data.db.internal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anggit97.data.db.internal.entity.RemoteKeys


/**
 * Created by Anggit Prayogo on 12,July,2021
 * GitHub : https://github.com/anggit97
 */
@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE movieId=:movieId")
    suspend fun remoteKeysMovieId(movieId: Long): RemoteKeys?

    @Query("SELECT * FROM remote_keys ")
    suspend fun allRemoteKeys(): List<RemoteKeys?>

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}