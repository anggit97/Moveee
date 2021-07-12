package com.anggit97.data.db.internal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.anggit97.data.db.internal.entity.FavouriteMovieEntity
import kotlinx.coroutines.flow.Flow


/**
 * Created by Anggit Prayogo on 06,July,2021
 * GitHub : https://github.com/anggit97
 */
@Dao
interface FavouriteMovieDao {

    @Query("SELECT * FROM favourite_movies")
    fun getFavouriteMovieList(): Flow<List<FavouriteMovieEntity>>

    @Insert(onConflict = REPLACE)
    suspend fun insertFavouriteMovie(movie: FavouriteMovieEntity)

    @Update(onConflict = REPLACE)
    suspend fun updateAll(movies: List<FavouriteMovieEntity>)

    @Query("DELETE FROM favourite_movies WHERE id=:movieId")
    suspend fun deleteFavouriteMovie(movieId: Int)

    @Query("SELECT COUNT(id) FROM FAVOURITE_MOVIES WHERE id=:movieId")
    suspend fun getCountForFavouriteMovie(movieId: Int): Int

    suspend fun isFavouriteMovie(movieId: Int): Boolean {
        return getCountForFavouriteMovie(movieId = movieId) > 0
    }
}