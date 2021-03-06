package com.anggit97.data.db.internal.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.anggit97.data.api.apiservice.MovieApiService
import com.anggit97.data.repository.internal.mapper.toMovieList
import com.anggit97.model.model.Movie


/**
 * Created by Anggit Prayogo on 14,July,2021
 * GitHub : https://github.com/anggit97
 */
class SearchMoviePagingSource(
    private val networkService: MovieApiService,
    private val query: String
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: 1

        return try {
            networkService.searchMovie(
                query = query,
                page = position.toString()
            ).run {
                val movies = toMovieList()

                LoadResult.Page(
                    data = movies,
                    prevKey = if (position == 1) null else position - 1,
                    nextKey = if (position == this.totalPages) null else position + 1
                )
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}