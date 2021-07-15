package com.anggit97.model.domain.moviesreminder

import com.anggit97.model.model.MovieDetail


/**
 * Created by Anggit Prayogo on 15,July,2021
 * GitHub : https://github.com/anggit97
 */
interface MoviesReminderUseCase {
    suspend fun getLatestMovie(): MovieDetail
}