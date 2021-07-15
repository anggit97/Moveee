package com.anggit97.data.db.internal.pagingsource

import androidx.paging.PagingSource
import com.anggit97.data.api.MovieeeApiService
import com.anggit97.data.api.response.MovieListResponse
import com.anggit97.data.api.response.MovieResponse
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
    lateinit var apiService: MovieeeApiService

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

object MovieDataFactories {
    val movieListResponse: MovieListResponse by lazy {
        MovieListResponse(
            datesResponse = null,
            page = null,
            movies = listOf(movieResponse1, movieResponse2),
            totalPages = null,
            totalResults = null
        )
    }


    private val movieResponse1 = MovieResponse(
        adult = null,
        backdropPath = null,
        genreIds = listOf(),
        id = 1,
        originalLanguage = null,
        originalTitle = null,
        overview = null,
        popularity = null,
        posterPath = null,
        releaseDate = null,
        title = null,
        video = null,
        voteAverage = null,
        voteCount = null
    )

    private val movieResponse2 = MovieResponse(
        adult = null,
        backdropPath = null,
        genreIds = listOf(),
        id = 2,
        originalLanguage = null,
        originalTitle = null,
        overview = null,
        popularity = null,
        posterPath = null,
        releaseDate = null,
        title = null,
        video = null,
        voteAverage = null,
        voteCount = null
    )


    val movie1 = Movie(
        id = 0,
        adult = false,
        backdropPath = "",
        genres = listOf(),
        movieId = 1,
        originalLanguage = null,
        originalTitle = "",
        overview = "",
        popularity = null,
        posterPath = "",
        releaseDate = "",
        title = "",
        video = false,
        voteAverage = 0.0,
        voteCount = 0
    )

    val movie2 = Movie(
        id = 0,
        adult = false,
        backdropPath = "",
        genres = listOf(),
        movieId = 2,
        originalLanguage = null,
        originalTitle = "",
        overview = "",
        popularity = null,
        posterPath = "",
        releaseDate = "",
        title = "",
        video = false,
        voteAverage = 0.0,
        voteCount = 0
    )
}
