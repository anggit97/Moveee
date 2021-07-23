package com.anggit97.data.db.internal.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.anggit97.data.api.apiservice.MovieApiService
import com.anggit97.data.db.MovieeeDatabase
import com.anggit97.data.db.internal.entity.MovieEntity
import com.anggit97.data.db.internal.entity.MovieNowRemoteKeys
import com.anggit97.data.repository.internal.mapper.toMovieListEntity
import java.io.InvalidObjectException

/**
 * Created by Anggit Prayogo on 12,July,2021
 * GitHub : https://github.com/anggit97
 */
@ExperimentalPagingApi
internal class MovieNowRemoteMediator(
    database: MovieeeDatabase,
    private val networkService: MovieApiService
) : RemoteMediator<Int, MovieEntity>() {

    val db = database.getMovieCacheDatabase()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                    ?: return MediatorResult.Error(InvalidObjectException("Result is empty"))

                val prevKey = remoteKeys.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                    ?: return MediatorResult.Error(InvalidObjectException("Result is empty"))

                val nextKey = remoteKeys.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                nextKey
            }
        }

        try {
            val response = networkService.getNowPlayingMovieList(page.toString())

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.movieNowRemoteKeysDao().clearRemoteKeys()
                    db.movieCacheDao().deleteAllByType(MovieEntity.TYPE_NOW)
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (response.endOfPage) null else page + 1
                val keys = response.movies?.map {
                    MovieNowRemoteKeys(
                        movieId = it.id ?: 0L,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                keys?.let { db.movieNowRemoteKeysDao().insertAll(it) }
                db.movieCacheDao()
                    .insertAll(response = response.toMovieListEntity(MovieEntity.TYPE_NOW))
            }

            return MediatorResult.Success(endOfPaginationReached = response.endOfPage)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    /**
     * get the closest remote key inserted which had the data
     */
    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieEntity>
    ): MovieNowRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.movieId?.let { id ->
                db.movieNowRemoteKeysDao().remoteKeysMovieId(id)
            }
        }
    }

    /**
     * get the last remote key inserted which had the data
     */
    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieEntity>): MovieNowRemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            db.movieNowRemoteKeysDao().remoteKeysMovieId(repo.movieId)
        }
    }

    /**
     * get the first remote key inserted which had the data
     */
    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>): MovieNowRemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { movie ->
            db.movieNowRemoteKeysDao().remoteKeysMovieId(movie.movieId)
        }
    }
}