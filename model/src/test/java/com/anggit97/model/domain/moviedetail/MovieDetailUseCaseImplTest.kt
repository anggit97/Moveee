package com.anggit97.model.domain.moviedetail

import com.anggit97.model.TestDataFactory
import com.anggit97.model.model.MovieDetail
import com.anggit97.model.repository.MovieeeRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Created by Anggit Prayogo on 15,July,2021
 * GitHub : https://github.com/anggit97
 */
class MovieDetailUseCaseImplTest {

    @MockK
    lateinit var mockRepository: MovieeeRepository

    private lateinit var sut: MovieDetailUseCase

    private var mockMovieId = "1"

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = MovieDetailUseCaseImpl(movieeeRepository = mockRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `success get movie detail return correct content`() = runBlockingTest {
        //expected
        val expectedMovieTitle = TestDataFactory.movieDetail.title //naruto

        val expectedMovieVideoTotal = TestDataFactory.movieDetail.videos?.size ?: 0
        val expectedMovieVideoTitle = TestDataFactory.movieDetail.videos?.first()?.name ?: ""

        val expectedMovieCastTotal = TestDataFactory.movieDetail.casts?.size ?: 0
        val expectedMovieCastTitle = TestDataFactory.movieDetail.casts?.first()?.name ?: ""

        //given
        coEvery {
            mockRepository.getMovieById(mockMovieId)
        }.returns(TestDataFactory.movieDetail)

        coEvery {
            mockRepository.getMovieVideos(mockMovieId)
        }.returns(TestDataFactory.videoList)

        coEvery {
            mockRepository.getMovieCredits(mockMovieId)
        }.returns(TestDataFactory.castList)


        //when
        val result: MovieDetail = sut.getMovieById(mockMovieId)



        //assert
        assertEquals(expectedMovieTitle, result.title)

        assertEquals(expectedMovieVideoTotal, result.videos?.size)
        assertEquals(expectedMovieVideoTitle, result.videos?.first()?.name)

        assertEquals(expectedMovieCastTotal, result.casts?.size)
        assertEquals(expectedMovieCastTitle, result.casts?.first()?.name)
    }
}