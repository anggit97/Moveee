package com.anggit97.model.domain.moviesreminder

import com.anggit97.model.model.MovieDetail
import com.anggit97.model.repository.MovieRepository
import javax.inject.Inject

class MoviesReminderUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : MoviesReminderUseCase {

    override suspend fun getLatestMovie(): MovieDetail {
        return movieRepository.getLatestMovie()
    }
}