package com.anggit97.model.domain.movielist

import com.anggit97.model.TestDataFactory
import com.anggit97.model.repository.MovieeeRepository
import com.anggit97.model.util.collectDataForTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by Anggit Prayogo on 15,July,2021
 * GitHub : https://github.com/anggit97
 */
class MovieListUseCaseImplTest {

    @MockK
    lateinit var mockRepository: MovieeeRepository

    private lateinit var sut: MovieListUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = MovieListUseCaseImpl(mockRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `success get movie list now returned correct title`() = runBlocking {
        val expectedTitleLast = TestDataFactory.movie3.title
        val actualLast: String

        val expectedTitleFirst = TestDataFactory.movie1.title
        val actualFirst: String

        //given
        coEvery { mockRepository.getNowMovieListPaging() }.returns(TestDataFactory.pagingDataMoviesFlow)

        //when
        val result = sut.getNowMovieListPaging()
        actualLast = result.last().collectDataForTest().map { it.title }.last()
        actualFirst = result.first().collectDataForTest().map { it.title }.first()

        //assert
        assertEquals(expectedTitleLast, actualLast)
        assertEquals(expectedTitleFirst, actualFirst)
    }
}

