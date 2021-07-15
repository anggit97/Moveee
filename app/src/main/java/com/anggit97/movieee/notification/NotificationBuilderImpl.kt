package com.anggit97.movieee.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.anggit97.core.ext.getColorCompat
import com.anggit97.core.notification.NotificationBuilder
import com.anggit97.model.model.MovieDetail
import com.anggit97.movieee.MainActivity
import com.anggit97.movieee.R
import javax.inject.Inject


/**
 * Created by Anggit Prayogo on 15,July,2021
 * GitHub : https://github.com/anggit97
 */
class NotificationBuilderImpl @Inject constructor(context: Context) : NotificationBuilder {

    private val applicationContext = context.applicationContext

    override fun showReminderLatestMovie(movie: MovieDetail) {
        NotificationSpecs.notifyOpenDateAlarm(applicationContext) {
            setStyle(NotificationCompat.BigTextStyle())
            setSmallIcon(R.drawable.ic_round_no_movies)
            setContentTitle(buildSpannedString { bold { append("Film ${movie.title} Terbaru! ⏰❤️") } })
            setContentText("Genks, film ini release pada ${movie.release_date}. jgn sampe ketinggalan ya!")
            setAutoCancel(true)
            setContentIntent(applicationContext.createLauncherIntent())
            setColor(applicationContext.getColorCompat(R.color.colorSecondary))
        }
    }

    private fun Context.createLauncherIntent(): PendingIntent {
        val intent = Intent(this, MainActivity::class.java)
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
    }
}