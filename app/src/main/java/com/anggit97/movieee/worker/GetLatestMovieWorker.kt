package com.anggit97.movieee.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.anggit97.core.notification.NotificationBuilder
import com.anggit97.core.util.currentTime
import com.anggit97.model.domain.moviesreminder.MoviesReminderUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import org.threeten.bp.temporal.ChronoUnit
import timber.log.Timber
import java.util.concurrent.TimeUnit
import kotlin.math.max


/**
 * Created by Anggit Prayogo on 15,July,2021
 * GitHub : https://github.com/anggit97
 */
@HiltWorker
class GetLatestMovieWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val reminderUseCase: MoviesReminderUseCase,
    private val notificationBuilder: NotificationBuilder
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Timber.d("doWork GetLatestMovie: start!")
        return try {
            val latestMovie = reminderUseCase.getLatestMovie()
            notificationBuilder.showReminderLatestMovie(latestMovie)
            Result.success()
        } catch (t: Throwable) {
            Timber.w(t)
            Result.failure()
        }
    }

    companion object{
        private const val DEBUG = false
        private const val TAG = "reminder_latest_movie"

        fun enqueuePeriodicWork(context: Context){
            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(TAG, ExistingPeriodicWorkPolicy.KEEP, createRequest())
        }

        private fun createRequest(): PeriodicWorkRequest {
            return PeriodicWorkRequestBuilder<GetLatestMovieWorker>(1, TimeUnit.MINUTES)
                .setInitialDelay(calculateInitialDelayMinutes(), TimeUnit.MINUTES)
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 1, TimeUnit.MINUTES)
                .build()
        }

        private fun calculateInitialDelayMinutes(): Long {
            if (DEBUG) {
                return 0
            }
            val current = currentTime()
            val rebirth = if (current.hour < 13) {
                current.withHour(13)
            } else {
                current.withHour(13).plusDays(1)
            }
            return max(0, current.until(rebirth, ChronoUnit.MINUTES))
        }
    }
}