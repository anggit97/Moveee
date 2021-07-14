package com.anggit97.model.domain.moviedetail

import com.anggit97.model.model.Cast
import com.anggit97.model.model.MovieDetail
import com.anggit97.model.model.Video
import com.anggit97.model.repository.MovieeeRepository
import javax.inject.Inject

class MovieDetailUseCaseImpl @Inject constructor(
    private val movieeeRepository: MovieeeRepository
) : MovieDetailUseCase {

    override suspend fun getMovieById(id: String): MovieDetail {
        return movieeeRepository.getMovieById(id)
    }

    override suspend fun getMovieVideos(id: String): List<Video> {
        return movieeeRepository.getMovieVideos(id)
    }

    override suspend fun getMovieCredits(id: String): List<Cast> {
        return movieeeRepository.getMovieCredits(id)
    }
}