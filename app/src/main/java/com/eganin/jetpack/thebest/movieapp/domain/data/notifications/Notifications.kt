package com.eganin.jetpack.thebest.movieapp.domain.data.notifications

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_HIGH
import androidx.core.net.toUri
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.Movie
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.MovieDetailsActivity

interface Notifications {
    fun init()
    fun showNotification(movie: Movie)
    fun dismissNotification(id: Int)
}

class MovieNotificationsManager(private val context: Context) : Notifications {

    private val notificationManagerCompat = NotificationManagerCompat.from(context)

    override fun init() {
        if (notificationManagerCompat.getNotificationChannel(CHANNEL_NEW_NOTIFICATIONS) == null) {
            val channel =
                NotificationChannelCompat.Builder(CHANNEL_NEW_NOTIFICATIONS, IMPORTANCE_HIGH)
                    .setName(context.getString(R.string.name_channel_notifications))
                    .setDescription(context.getString(R.string.channel_description_notification))
                    .build()

            notificationManagerCompat.createNotificationChannel(channel)
        }
    }

    @WorkerThread
    override fun showNotification(movie: Movie) {
        Log.d("EEE","NOTIFICATION")
        val uri = "https://android.movieapp/movies/${movie.id}".toUri()
        val intent = Intent(context, MovieDetailsActivity::class.java)
            .setAction(Intent.ACTION_VIEW)
            .setData(uri)
        val pendingIntent = PendingIntent.getActivity(
            context,
            REQUEST_CONTENT,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_NEW_NOTIFICATIONS)
            .setContentTitle(movie.title)
            .setContentText(movie.originalTitle)
            .setSmallIcon(R.drawable.ic_baseline_movie_24)
            .setPriority(IMPORTANCE_HIGH)
            .setContentIntent(pendingIntent)
            .setWhen(System.currentTimeMillis())


        notificationManagerCompat.notify(
            TAG,
            movie.id,
            notificationBuilder.build()
        )
    }

    override fun dismissNotification(id: Int) = notificationManagerCompat.cancel(TAG, id)

    companion object {
        private const val CHANNEL_NEW_NOTIFICATIONS = "new_notifications"
        private const val REQUEST_CONTENT = 80
        private const val TAG = "movie_notification"
    }
}