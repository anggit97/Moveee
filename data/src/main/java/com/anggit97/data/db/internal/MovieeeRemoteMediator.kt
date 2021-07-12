package com.anggit97.data.db.internal

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.anggit97.data.api.MovieeeApiService
import com.anggit97.data.db.MoveeeDatabase
import com.anggit97.data.db.internal.entity.MovieEntity
import com.anggit97.data.db.internal.entity.MovieListEntity
import com.anggit97.data.db.internal.entity.RemoteKeys
import com.anggit97.data.repository.internal.DataMoveeeRepository.Companion.DEFAULT_PAGE_INDEX
import com.anggit97.data.repository.internal.mapper.toMovieListEntity
import com.anggit97.model.Movie
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException


/**
 * Created by Anggit Prayogo on 12,July,2021
 * GitHub : https://github.com/anggit97
 */
@ExperimentalPagingApi
internal class MovieeeRemoteMediator(
    private val type: String,
    database: MoveeeDatabase,
    private val networkService: MovieeeApiService
) : RemoteMediator<Int, MovieEntity>() {

    val db = database.getMovieCacheDatabase()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            val response = networkService.getNowPlayingMovieList(page.toString())
            val isEndOfList = response.movies.isNullOrEmpty()
            db.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeysDao().clearRemoteKeys()
                    db.movieCacheDao().deleteAll()
                }
                val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = response.movies?.map {
                    RemoteKeys(movieId = it.id.toString(), prevKey = prevKey, nextKey = nextKey)
                } ?: emptyList()
                db.remoteKeysDao().insertAll(keys)
                db.movieCacheDao().insert(response = response.toMovieListEntity(type))
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    /**
     * this returns the page key or the final end of list success result
     */
    suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, MovieEntity>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: DEFAULT_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                    ?: throw InvalidObjectException("Remote key should not be null for $loadType")
                remoteKeys.nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                    ?: throw InvalidObjectException("Invalid state, key should not be null")
                //end of list condition reached
                remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prevKey
            }
        }
    }

    /**
     * get the closest remote key inserted which had the data
     */
    private suspend fun getClosestRemoteKey(state: PagingState<Int, MovieEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                db.remoteKeysDao().remoteKeysMovieId(repoId.toString())
            }
        }
    }

    /**
     * get the last remote key inserted which had the data
     */
    private suspend fun getLastRemoteKey(state: PagingState<Int, MovieEntity>): RemoteKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { movie -> db.remoteKeysDao().remoteKeysMovieId(movie.id.toString()) }
    }

    /**
     * get the first remote key inserted which had the data
     */
    private suspend fun getFirstRemoteKey(state: PagingState<Int, MovieEntity>): RemoteKeys? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { movie -> db.remoteKeysDao().remoteKeysMovieId(movie.id.toString()) }
    }
}