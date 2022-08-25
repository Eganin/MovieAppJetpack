package com.eganin.jetpack.thebest.movieapp.di

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.domain.data.database.MovieDatabase
import com.eganin.jetpack.thebest.movieapp.domain.data.notifications.MovieNotificationsManager
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.details.MovieDetailsRepository
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.details.MovieDetailsRepositoryImpl
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.list.MovieRepository
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.list.MovieRepositoryImpl
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.workmanager.WorkerRepository
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.workmanager.WorkerRepositoryImpl
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.details.MovieDetailsViewModel
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list.MoviesListViewModel
import java.util.*


class AppComponent(applicationContext: Context) {

    private val defaultLanguage = Locale.getDefault().language
    val database = MovieDatabase.create(applicationContext)

    val movieRepository: MovieRepository =
        MovieRepositoryImpl(language = defaultLanguage, database = database)

    private val movieDetailsRepository: MovieDetailsRepository = MovieDetailsRepositoryImpl(
        language = defaultLanguage,
        database = database
    )
    val workerRepository: WorkerRepository = WorkerRepositoryImpl()

    val notificationManager = MovieNotificationsManager(context = applicationContext)

    @Composable
    fun getMoviesListViewModel() = viewModel<MoviesListViewModel>(
        factory = MoviesListViewModel.Factory(
            repository = movieRepository,
            notificationsManager = notificationManager,
        )
    )

    @Composable
    fun getMovieDetailsViewModel() = viewModel<MovieDetailsViewModel>(
        factory = MovieDetailsViewModel.Factory(
            repository = movieDetailsRepository,
        )
    )
}