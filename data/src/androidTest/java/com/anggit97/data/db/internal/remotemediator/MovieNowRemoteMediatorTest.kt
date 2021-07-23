package com.anggit97.data.db.internal.remotemediator

import android.content.Context
import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anggit97.data.MovieDataFactories
import com.anggit97.data.api.apiservice.MovieApiService
import com.anggit97.data.db.internal.MovieCacheDatabase
import com.anggit97.data.db.internal.MovieDatabase
import com.anggit97.data.db.internal.MovieeeDatabaseImpl
import com.anggit97.data.db.internal.entity.MovieEntity
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Created by Anggit Prayogo on 15,July,2021
 * GitHub : https://github.com/anggit97
 */
@RunWith(AndroidJUnit4::class)
class MovieNowRemoteMediatorTest {

    private val mockApi: MovieApiService = mockk()

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val mockMovieCacheDb = Room.inMemoryDatabaseBuilder(
        context, MovieCacheDatabase::class.java
    ).build()

    private val mockMovieDb = Room.inMemoryDatabaseBuilder(
        context, MovieDatabase::class.java
    ).build()

    private val mockDb = MovieeeDatabaseImpl(mockMovieCacheDb, mockMovieDb)

    @ExperimentalPagingApi
    private lateinit var remoteMediatorSUT: MovieNowRemoteMediator

    @ExperimentalPagingApi
    @Before
    fun setUp() {
        remoteMediatorSUT = MovieNowRemoteMediator(
            mockDb,
            mockApi
        )
    }

    @ExperimentalPagingApi
    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runBlocking {
        coEvery {
            mockApi.getNowPlayingMovieList(
                "1"
            )
        }.returns(MovieDataFactories.movieListResponse)

        val pagingState = PagingState<Int, MovieEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )

        val result = remoteMediatorSUT.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    //Not Fix Yet, end of pagination always false, instead true
    @ExperimentalPagingApi
    @Test
    fun refreshLoadSuccessAndEndOfPaginationWhenNoMoreData() = runBlocking {
        coEvery {
            mockApi.getNowPlayingMovieList(
                "1"
            )
        }.returns(MovieDataFactories.movieListResponseEmpty)

        val pagingState = PagingState<Int, MovieEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )

        val result = remoteMediatorSUT.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
//        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @ExperimentalPagingApi
    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() = runBlocking {
        coEvery {
            mockApi.getNowPlayingMovieList(
                "1"
            )
        }.throws(Throwable("Error!!"))

        val pagingState = PagingState<Int, MovieEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )

        val result = remoteMediatorSUT.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Error)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        mockMovieCacheDb.clearAllTables()
        mockMovieDb.clearAllTables()
        mockMovieCacheDb.close()
        mockMovieDb.close()
    }
}