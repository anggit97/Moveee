package com.anggit97.data.repository.internal

import androidx.paging.*
import com.anggit97.data.api.apiservice.MovieApiService
import com.anggit97.data.db.MovieeeDatabase
import com.anggit97.data.db.internal.mapper.toMovie
import com.anggit97.data.db.internal.pagingsource.SearchMoviePagingSource
import com.anggit97.data.db.internal.remotemediator.MovieNowRemoteMediator
import com.anggit97.data.db.internal.remotemediator.MoviePlanRemoteMediator
import com.anggit97.data.repository.internal.mapper.toCastList
import com.anggit97.data.repository.internal.mapper.toMovieDetail
import com.anggit97.data.repository.internal.mapper.toMovieVideos
import com.anggit97.model.model.Cast
import com.anggit97.model.model.Movie
import com.anggit97.model.model.MovieDetail
import com.anggit97.model.model.Video
import com.anggit97.model.repository.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

/**
 * Created by Anggit Prayogo on 03,July,2021
 * GitHub : https://github.com/anggit97
 */
class MovieRepositoryImpl(
    private val local: MovieeeDatabase,
    private val movieApiService: MovieApiService,
) : MovieRepository {

    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    override fun getNowMovieListPaging(): Flow<PagingData<Movie>> {
        val pagingSourceFactory = { local.getNowMovieListNowPaging() }
        val pageConfig = getDefaultPageConfig()

        return Pager(
            config = pageConfig,
            remoteMediator = MovieNowRemoteMediator(local, movieApiService),
            pagingSourceFactory = pagingSourceFactory,
        ).flow.mapLatest { it.map { movieEntity -> movieEntity.toMovie() } }
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(
            pageSize = 20,
            enablePlaceholders = true,
            prefetchDistance = 5,
            initialLoadSize = 40
        )
    }

    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    override fun getPlanMovieList(): Flow<PagingData<Movie>> {
        val pagingSourceFactory = { local.getPlanMovieListFlow() }
        val pageConfig = getDefaultPageConfig()

        return Pager(
            config = pageConfig,
            remoteMediator = MoviePlanRemoteMediator(local, movieApiService),
            pagingSourceFactory = pagingSourceFactory,
        ).flow.mapLatest { it.map { movieEntity -> movieEntity.toMovie() } }
    }

    override suspend fun getMovieById(id: String): MovieDetail {
        return movieApiService.getMovieById(id).toMovieDetail()
    }

    @ExperimentalCoroutinesApi
    override fun getFavoriteMovieList(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
        ) {
            local.getFavoriteMovieList()
        }.flow.mapLatest { it.map { favouriteMovieEntity -> favouriteMovieEntity.toMovie() } }
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

    override suspend fun searchMovie(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { SearchMoviePagingSource(movieApiService, query) }
        ).flow
    }

    override suspend fun getLatestMovie(): MovieDetail {
        return movieApiService.getLatestMovie().toMovieDetail()
    }

    /**
     * Movie Video
     */
    override suspend fun getMovieVideos(id: String): List<Video> {
        return movieApiService.getMovieVideos(id).toMovieVideos()
    }

    /**
     * Movie Credits
     */
    override suspend fun getMovieCredits(id: String): List<Cast> {
        return movieApiService.getMovieCredits(id).toCastList()
    }
}