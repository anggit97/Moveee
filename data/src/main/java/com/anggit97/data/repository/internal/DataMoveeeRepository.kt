package com.anggit97.data.repository.internal

import androidx.paging.*
import com.anggit97.data.api.MovieeeApiService
import com.anggit97.data.db.MoveeeDatabase
import com.anggit97.data.db.internal.MovieeeRemoteMediator
import com.anggit97.data.db.internal.entity.MovieListEntity.Companion.TYPE_NOW
import com.anggit97.data.db.internal.mapper.toMovie
import com.anggit97.data.repository.internal.mapper.toCastList
import com.anggit97.data.repository.internal.mapper.toMovieDetail
import com.anggit97.data.repository.internal.mapper.toMovieList
import com.anggit97.data.repository.internal.mapper.toMovieVideos
import com.anggit97.model.Cast
import com.anggit97.model.Movie
import com.anggit97.model.MovieDetail
import com.anggit97.model.Video
import com.anggit97.model.repository.MovieeeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest


/**
 * Created by Anggit Prayogo on 03,July,2021
 * GitHub : https://github.com/anggit97
 */
class DataMoveeeRepository(
    private val local: MoveeeDatabase,
    private val remote: MovieeeApiService
) : MovieeeRepository {

    override fun getNowMovieList(): Flow<List<Movie>> {
        return local.getNowMovieListFlow()
    }

    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    override fun getNowMovieListPaging(): Flow<PagingData<Movie>> {
        val pagingSourceFactory = { local.getNowMovieListNowPaging() }
        val pageConfig = getDefaultPageConfig()

        return Pager(
            config = pageConfig,
            remoteMediator = MovieeeRemoteMediator(TYPE_NOW, local, remote),
            pagingSourceFactory = pagingSourceFactory,
        ).flow.mapLatest { it.map { movieEntity -> movieEntity.toMovie() } }
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE,
            enablePlaceholders = false,
            prefetchDistance = 1,
            initialLoadSize = DEFAULT_PAGE_SIZE
        )
    }

    override suspend fun updateNowMovieList() {
        local.saveNowMovieList(remote.getNowPlayingMovieList("1").toMovieList())
    }

    override suspend fun updateAndGetNowMovieList(): List<Movie> {
        return emptyList()
    }

    override fun getPlanMovieList(): Flow<List<Movie>> {
        return local.getPlanMovieListFlow()
    }

    override suspend fun updatePlanMovieList() {
        val remoteResult = remote.getUpcomingMovieList().toMovieList()
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

    override suspend fun removeFavoriteMovie(movieId: Long) {
        local.removeFavoriteMovie(movieId)
    }

    override suspend fun isFavoriteMovie(movieId: Long): Boolean {
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


    /**
     * Movie Video
     */
    override suspend fun getMovieVideos(id: String): List<Video> {
        return remote.getMovieVideos(id).toMovieVideos()
    }

    /**
     * Movie Credits
     */
    override suspend fun getMovieCredits(id: String): List<Cast> {
        return remote.getMovieCredits(id).toCastList()
    }

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20
    }
}