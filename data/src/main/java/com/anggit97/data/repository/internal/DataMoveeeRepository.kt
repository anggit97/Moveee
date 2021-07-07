package com.anggit97.data.repository.internal

import com.anggit97.core.util.NetworkCheckerHelper
import com.anggit97.data.api.MovieeeApiService
import com.anggit97.data.db.MoveeeDatabase
import com.anggit97.data.repository.internal.mapper.toMovieDetail
import com.anggit97.data.repository.internal.mapper.toMovieList
import com.anggit97.model.Movie
import com.anggit97.model.MovieDetail
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
        val remoteResult = remote.getPopularMovieList().toMovieList()
        local.savePlanMovieList(remoteResult)
    }

    override suspend fun updateAndGetPlanMovieList(): List<Movie> {
        return emptyList()
    }

    override suspend fun getMovieById(id: String): MovieDetail {
        return remote.getMovieById(id).toMovieDetail()
    }

    override fun getFavoriteMovieList(): Flow<List<Movie>> {
        return local.getFavoriteMovieList()
    }

    override suspend fun addFavoriteMovie(movie: Movie) {
        local.addFavoriteMovie(movie)
    }

    override suspend fun removeFavoriteMovie(movieId: Int) {
        local.removeFavoriteMovie(movieId)
    }

    override suspend fun isFavoriteMovie(movieId: Int): Boolean {
        return local.isFavoriteMovie(movieId)
    }

    override suspend fun searchMovie(query: String): List<Movie> {
        return local.getAllMovieList().asSequence()
            .filter { it.isMatchedWith(query) }
            .distinct()
            .toList()
    }


    private fun Movie.isMatchedWith(query: String): Boolean {
        return SearchHelper.matched(title, query)
    }
}