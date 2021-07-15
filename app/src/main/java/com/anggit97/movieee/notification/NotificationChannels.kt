package com.anggit97.movieee.notification

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.anggit97.movieee.R

object NotificationChannels {

    const val NOTICE = "NTC"
    const val EVENT = "EVT" // Default
    const val THEATER_MODE = "THM"
    const val REMINDER_LATEST_MOVIE = "RMD"

    internal fun initialize(application: Application) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            application.getNotificationManager()?.run {
                val notice = NotificationChannel(
                    NOTICE,
                    application.getString(R.string.notification_channel_notice),
                    NotificationManager.IMPORTANCE_HIGH
                )
                val event = NotificationChannel(
                    EVENT,
                    application.getString(R.string.notification_channel_event),
                    NotificationManager.IMPORTANCE_HIGH
                )
                val reminderLatestMovie = NotificationChannel(
                    REMINDER_LATEST_MOVIE,
                    application.getString(R.string.notification_channel_reminder_latest_movie),
                    NotificationManager.IMPORTANCE_HIGH
                )
                createNotificationChannels(listOf(notice, event, reminderLatestMovie))
            }
        }
    }
}
