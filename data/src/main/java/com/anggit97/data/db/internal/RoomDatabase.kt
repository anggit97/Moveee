package com.anggit97.data.db.internal

import androidx.paging.PagingSource
import com.anggit97.data.db.MoveeeDatabase
import com.anggit97.data.db.internal.entity.MovieEntity
import com.anggit97.data.db.internal.entity.MovieListEntity.Companion.TYPE_NOW
import com.anggit97.data.db.internal.entity.MovieListEntity.Companion.TYPE_POPULAR
import com.anggit97.data.db.internal.mapper.toFavouriteEntity
import com.anggit97.data.db.internal.mapper.toMovie
import com.anggit97.data.db.internal.mapper.toMovieEntityList
import com.anggit97.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber


/**
 * Created by Anggit Prayogo on 02,July,2021
 * GitHub : https://github.com/anggit97
 */
class RoomDatabase(
    private val cacheMovieDatabase: MovieCacheDatabase,
    private val movieDatabase: MovieDatabase
) : MoveeeDatabase {

    override suspend fun saveNowMovieList(movieList: List<Movie>) {
        saveMovieListAs(TYPE_NOW, movieList)
    }

    private suspend fun saveMovieListAs(type: String, movieList: List<Movie>) {
        cacheMovieDatabase.movieCacheDao().insertAll(
            movieList.toMovieEntityList(type)
        )
    }

    override fun getMovieCacheDatabase(): MovieCacheDatabase {
        return cacheMovieDatabase
    }

    override fun getMovieeeDatabase(): MovieDatabase {
        return movieDatabase
    }

    override fun getNowMovieListNowPaging(): PagingSource<Int, MovieEntity> {
        return cacheMovieDatabase.movieCacheDao().getMovieListByTypePaging(TYPE_NOW)
    }

    override fun getNowMovieListFlow(): Flow<List<Movie>> {
        return getMovieList(TYPE_NOW)
    }

    private fun getMovieList(type: String): Flow<List<Movie>> {
        return cacheMovieDatabase.movieCacheDao().getMovieListByType(type)
            .map { it.map { it.toMovie() } }
            .catch {
                emit(
                    emptyList()
                )
            }
    }

    override suspend fun getNowMovieList(): List<Movie> {
        return getMovieListOf(TYPE_NOW)
    }

    override suspend fun getPlanMovieList(): List<Movie> {
        return getMovieListOf(TYPE_POPULAR)
    }

    private suspend fun getMovieListOf(type: String): List<Movie> {
        return try {
            cacheMovieDatabase.movieCacheDao().findByType(type)
                .map { movieEntity -> movieEntity.toMovie() }
        } catch (t: Throwable) {
            Timber.w(t)
            emptyList()
        }
    }


    override suspend fun savePlanMovieList(movieList: List<Movie>) {
        saveMovieListAs(TYPE_POPULAR, movieList)
    }

    override fun getPlanMovieListFlow(): Flow<List<Movie>> {
        return getMovieList(TYPE_POPULAR)
    }

    override suspend fun addFavoriteMovie(movie: Movie) {
        movieDatabase.favouriteMovieDao().insertFavouriteMovie(movie.toFavouriteEntity())
    }

    override suspend fun removeFavoriteMovie(movieId: Long) {
        movieDatabase.favouriteMovieDao().deleteFavouriteMovie(movieId)
    }

    override fun getFavoriteMovieList(): Flow<List<Movie>> {
        return movieDatabase.favouriteMovieDao().getFavouriteMovieList().map {
            it.map { favoriteMovieEntity -> favoriteMovieEntity.toMovie() }
        }
    }

    override suspend fun isFavoriteMovie(movieId: Long): Boolean {
        return movieDatabase.favouriteMovieDao().isFavouriteMovie(movieId)
    }

    override suspend fun getAllMovieList(): List<Movie> {
        return getMovieListOf(TYPE_POPULAR) + getMovieListOf(TYPE_NOW)
    }
}