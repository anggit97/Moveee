package com.anggit97.model.domain.moviedetail

import com.anggit97.model.model.MovieDetail
import com.anggit97.model.repository.MovieeeRepository
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class MovieDetailUseCaseImpl @Inject constructor(
    private val movieeeRepository: MovieeeRepository
) : MovieDetailUseCase {

    @InternalCoroutinesApi
    override suspend fun getMovieById(id: String): MovieDetail {
        val detailMovieFlow = flowOf(movieeeRepository.getMovieById(id))
        val movieVideosFlow = flowOf(movieeeRepository.getMovieVideos(id))
        val movieCreditsFlow = flowOf(movieeeRepository.getMovieCredits(id))
        return combine(
            detailMovieFlow,
            movieVideosFlow,
            movieCreditsFlow
        ) { detailMovie, movieVideo, movieCredit ->
            detailMovie.videos = movieVideo
            detailMovie.casts = movieCredit
            detailMovie
        }.single()
    }
}