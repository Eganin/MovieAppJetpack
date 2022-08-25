package com.eganin.jetpack.thebest.movieapp.di

import android.content.Context
import com.eganin.jetpack.thebest.movieapp.domain.data.database.MovieDatabase
import com.eganin.jetpack.thebest.movieapp.domain.data.notifications.MovieNotificationsManager
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.details.MovieDetailsRepository
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.details.MovieDetailsRepositoryImpl
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.list.MovieRepository
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.list.MovieRepositoryImpl
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.workmanager.WorkerRepository
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.workmanager.WorkerRepositoryImpl
import java.util.*


class AppComponent(applicationContext: Context) {

    private val defaultLanguage = Locale.getDefault().language
    val database = MovieDatabase.create(applicationContext)

    private val movieRepository: MovieRepository =
        MovieRepositoryImpl(language = defaultLanguage, database = database)

    val movieDetailsRepository: MovieDetailsRepository = MovieDetailsRepositoryImpl(
        language = defaultLanguage,
        database = database
    )
    private val workerRepository: WorkerRepository = WorkerRepositoryImpl()

    private val notificationManager = MovieNotificationsManager(context = applicationContext)

    fun getMovieRepository() = movieRepository

    fun getWorkerRepository() = workerRepository

    fun getNotificationManager()= notificationManager

}