package com.anggit97.data.repository.internal

import com.anggit97.core.util.NetworkCheckerHelper
import com.anggit97.data.api.MovieeeApiService
import com.anggit97.data.db.MoveeeDatabase
import com.anggit97.data.repository.internal.mapper.toMovieList
import com.anggit97.model.Movie
import com.anggit97.model.repository.MovieeeRepository
import kotlinx.coroutines.flow.Flow


/**
 * Created by Anggit Prayogo on 03,July,2021
 * GitHub : https://github.com/anggit97
 */
internal class DataMoveeeRepository(
    private val local: MoveeeDatabase,
    private val remote: MovieeeApiService,
    private val networkChecker: NetworkCheckerHelper
) : MovieeeRepository {

    override fun getNowMovieList(): Flow<List<Movie>> {
        return local.getNowMovieListFlow()
    }

    override suspend fun updateNowMovieList() {
        val isNetworkActive = networkChecker.isNetworkActive()
        local.saveNowMovieList(remote.getNowPlayingMovieList().toMovieList())
    }

    override suspend fun updateAndGetNowMovieList(): List<Movie> {
        return emptyList()
    }

    override fun getPlanMovieList(): Flow<List<Movie>> {
        return local.getPlanMovieListFlow()
    }

    override suspend fun updatePlanMovieList() {
        local.savePlanMovieList(remote.getPopularMovieList().toMovieList())
    }

    override suspend fun updateAndGetPlanMovieList(): List<Movie> {
        return emptyList()
    }
}