package com.anggit97.model.domain.moviedetail

import com.anggit97.model.model.MovieDetail
import com.anggit97.model.repository.MovieRepository
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class MovieDetailUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : MovieDetailUseCase {

    @InternalCoroutinesApi
    override suspend fun getMovieById(id: String): MovieDetail {
        val detailMovieFlow = flowOf(movieRepository.getMovieById(id))
        val movieVideosFlow = flowOf(movieRepository.getMovieVideos(id))
        val movieCreditsFlow = flowOf(movieRepository.getMovieCredits(id))
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