package com.anggit97.data.db.internal

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.anggit97.data.api.MovieeeApiService
import com.anggit97.data.db.MoveeeDatabase
import com.anggit97.data.db.internal.entity.MovieEntity
import com.anggit97.data.db.internal.entity.RemoteKeys
import com.anggit97.data.repository.internal.DataMoveeeRepository.Companion.DEFAULT_PAGE_INDEX
import com.anggit97.data.repository.internal.mapper.toMovieListEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException


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

    override suspend fun initialize(): InitializeAction {
        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded. In cases where we don't mind showing out-of-date,
        // cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
        // triggering remote refresh.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val pageKeyData = getKeyPageData(loadType, state)
        Log.d(TAG, "load: $pageKeyData")
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        Log.d(TAG, "load 2: $page")

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
                }?: emptyList()
                keys.map { Log.d(TAG, "load: KEYS ${it.movieId} + prev key ${it.prevKey} + next key ${it.nextKey}") }
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
    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, MovieEntity>): Any {
        Log.d(TAG, "getKeyPageData: 1")
        return when (loadType) {
            LoadType.REFRESH -> {
                Log.d(TAG, "getKeyPageData 2: ")
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                val key = remoteKeys?.nextKey?.minus(1) ?: DEFAULT_PAGE_INDEX
                Log.d(TAG, "getKeyPageData 6: $key")
                key
            }
            LoadType.APPEND -> {
                Timber.d("getKeyPageData 3")
                val key = db.withTransaction {
                    db.remoteKeysDao().allRemoteKeys().lastOrNull() // Workaround
                }
                key?.nextKey ?: return MediatorResult.Success(true)
//                val remoteKeys = getRemoteKeyForLastItem(state)
//                val nextKey = remoteKeys?.nextKey
//                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
//                Log.d(TAG, "getKeyPageData 5: $nextKey")
//                nextKey
            }
            LoadType.PREPEND -> {
                Timber.d("getKeyPageData 4")
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                Log.d(TAG, "getKeyPageData 7: $prevKey")
                prevKey
            }
        }
    }

    /**
     * get the closest remote key inserted which had the data
     */
    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieEntity>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                db.remoteKeysDao().remoteKeysMovieId(repoId.toString())
            }
        }
    }

    /**
     * get the last remote key inserted which had the data
     */
    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieEntity>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        Log.d(TAG, "getRemoteKeyForLastItem: 1")
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                Log.d(TAG, "getRemoteKeyForLastItem: 2 - ${repo.id}")
                db.remoteKeysDao().remoteKeysMovieId(repo.id.toString())
            }
    }

    /**
     * get the first remote key inserted which had the data
     */
    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                db.remoteKeysDao().remoteKeysMovieId(repo.id.toString())
            }
    }

    private val TAG = "MovieeeRemote"
}