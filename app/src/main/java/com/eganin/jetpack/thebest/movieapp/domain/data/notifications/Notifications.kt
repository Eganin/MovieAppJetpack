package com.eganin.jetpack.thebest.movieapp.domain.data.notifications

import android.annotation.SuppressLint
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

    private val notificationManager = NotificationManagerCompat.from(context)

    override fun init() {
        if (notificationManager.getNotificationChannel(CHANNEL_MOVIES) == null) {
            val channel =
                NotificationChannelCompat.Builder(CHANNEL_MOVIES, IMPORTANCE_HIGH)
                    .setName(context.getString(R.string.name_channel_notifications))
                    .setDescription(context.getString(R.string.channel_description_notification))
                    .build()

            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    @WorkerThread
    override fun showNotification(movie: Movie) {
        val uri = "https://android.movieapp/movies/${movie.id}".toUri()

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_MOVIES)
            .setContentTitle(movie.title)
            .setContentText(movie.originalTitle)
            .setSmallIcon(R.drawable.ic_baseline_movie_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    REQUEST_CONTENT_MOVIES,
                    Intent(context, MovieDetailsActivity::class.java)
                        .setAction(Intent.ACTION_VIEW)
                        .setData(uri),
                    PendingIntent.FLAG_CANCEL_CURRENT
                )
            )
            .setWhen(System.currentTimeMillis())

        notificationManager.notify(
            MOVIE_TAG,
            movie.id,
            notificationBuilder.build()
        )
    }

    fun showTestNotification(){
        val uri = "https://android.movieapp/movies/616037".toUri()
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_MOVIES)
            .setContentTitle("movie.title")
            .setContentText("movie.originalTitle")
            .setSmallIcon(R.drawable.ic_baseline_movie_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    REQUEST_CONTENT_MOVIES,
                    Intent(context, MovieDetailsActivity::class.java)
                        .setAction(Intent.ACTION_VIEW)
                        .setData(uri),
                    PendingIntent.FLAG_MUTABLE
                )
            )
            .setWhen(System.currentTimeMillis())

        notificationManager.notify(
            MOVIE_TAG,
            616037,
            notificationBuilder.build()
        )
    }

    override fun dismissNotification(id: Int) = notificationManager.cancel(MOVIE_TAG, id)

    companion object {
        private const val CHANNEL_MOVIES = "channel_movies"
        private const val REQUEST_CONTENT_MOVIES = 80
        private const val MOVIE_TAG = "movies"
    }
}