package com.anggit97.model.domain.moviesreminder

import com.anggit97.model.model.MovieDetail
import com.anggit97.model.repository.MovieeeRepository
import javax.inject.Inject

class MoviesReminderUseCaseImpl @Inject constructor(
    private val movieeeRepository: MovieeeRepository
) : MoviesReminderUseCase {

    override suspend fun getLatestMovie(): MovieDetail {
        return movieeeRepository.getLatestMovie()
    }
}