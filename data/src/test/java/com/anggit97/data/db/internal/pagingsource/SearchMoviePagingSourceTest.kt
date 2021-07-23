package com.anggit97.data.db.internal.pagingsource

import androidx.paging.PagingSource
import com.anggit97.data.api.apiservice.MovieApiService
import com.anggit97.data.db.internal.MovieDataFactories
import com.anggit97.model.model.Movie
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

/**
 * Created by Anggit Prayogo on 15,July,2021
 * GitHub : https://github.com/anggit97
 */
class SearchMoviePagingSourceTest {

    private val mockMovies = listOf(
        MovieDataFactories.movie1,
        MovieDataFactories.movie2
    )

    private val mockQuery = "naruto"

    private lateinit var pagingSourceUAT: PagingSource<Int, Movie>

    @MockK
    lateinit var apiService: MovieApiService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        pagingSourceUAT = SearchMoviePagingSource(networkService = apiService, query = mockQuery)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `load return page when on successful load item key data`() = runBlockingTest {

        coEvery {
            apiService.searchMovie(
                mockQuery,
                "1"
            )
        }.returns(MovieDataFactories.movieListResponse)

        val expected: Any = PagingSource.LoadResult.Page(
            data = listOf(mockMovies[0], mockMovies[1]),
            prevKey = null,
            nextKey = 2
        )

        val actual: Any = pagingSourceUAT.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        assertEquals(expected, actual)
    }
}
