package com.eganin.jetpack.thebest.movieapp.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.eganin.jetpack.thebest.movieapp.domain.data.database.MovieDatabase
import com.eganin.jetpack.thebest.movieapp.domain.data.notifications.MovieNotificationsManager
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.details.MovieDetailsRepository
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.details.MovieDetailsRepositoryImpl
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.list.MovieRepository
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.list.MovieRepositoryImpl
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.workmanager.WorkerRepository
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.workmanager.WorkerRepositoryImpl
import com.eganin.jetpack.thebest.movieapp.ui.presentation.utils.isConnection
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details.MovieDetailsViewModel
import java.util.*


class AppComponent(applicationContext: Context) {

    private val defaultLanguage = Locale.getDefault().language
    val database = MovieDatabase.create(applicationContext)
    private val sharedPreferencesMovieType =
        applicationContext.getSharedPreferences(SHARED_PREFERENCES_TAG_MOVIE, MODE_PRIVATE)

    private val sharedPreferencesRationalShown = applicationContext.getSharedPreferences(
        SHARED_PREFERENCES_TAG_RATIONAL_SHOWN, MODE_PRIVATE
    )

    private val movieRepository: MovieRepository =
        MovieRepositoryImpl(language = defaultLanguage, database = database)
    val movieDetailsRepository: MovieDetailsRepository = MovieDetailsRepositoryImpl(
        language = defaultLanguage,
        database = database
    )
    private val workerRepository: WorkerRepository = WorkerRepositoryImpl()

    val connection = isConnection(context = applicationContext)

    private val notificationManager = MovieNotificationsManager(context = applicationContext)


    fun getMoviesDetailsRepository(fragment: Fragment): MovieDetailsViewModel {
        return ViewModelProvider(
            fragment,
            MovieDetailsViewModel.Factory(
                repository = movieDetailsRepository,
                isConnection = connection,
            )
        )[MovieDetailsViewModel::class.java]
    }


    fun getMovieRepository() = movieRepository

    fun getSharedPreferencesMovieType() = sharedPreferencesMovieType

    fun getSharedPreferencesRationalShown() = sharedPreferencesRationalShown

    fun getWorkerRepository() = workerRepository

    fun getNotificationManager()= notificationManager

    companion object {
        private const val SHARED_PREFERENCES_TAG_MOVIE = "MOVIE_CHOICE"
        private const val SHARED_PREFERENCES_TAG_RATIONAL_SHOWN = "MOVIE_CHOICE"
        const val TOKEN_CHOICE_MOVIE = "TOKEN_CHOICE_MOVIE"
    }

}