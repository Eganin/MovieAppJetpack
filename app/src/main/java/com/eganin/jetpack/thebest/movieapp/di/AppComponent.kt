package com.eganin.jetpack.thebest.movieapp.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.eganin.jetpack.thebest.movieapp.domain.data.database.MovieDatabase
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.details.MovieDetailsRepository
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.details.MovieDetailsRepositoryImpl
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.list.MovieRepository
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.list.MovieRepositoryImpl
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.workmanager.WorkerRepository
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.workmanager.WorkerRepositoryImpl
import com.eganin.jetpack.thebest.movieapp.ui.presentation.utils.isConnection
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details.MovieDetailsViewModel
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.MoviesListViewModel
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.MovieDetailsActivity
import java.util.*


class AppComponent(applicationContext: Context) {

    private val defaultLanguage = Locale.getDefault().language
    val database = MovieDatabase.create(applicationContext)
    private val sharedPreferences =
        applicationContext.getSharedPreferences(SHARED_PREFERENCES_TAG, MODE_PRIVATE)

    private val movieRepository: MovieRepository =
        MovieRepositoryImpl(language = defaultLanguage, database = database)
    private val movieDetailsRepository: MovieDetailsRepository = MovieDetailsRepositoryImpl(
        language = defaultLanguage,
        database = database
    )
    private val workerRepository : WorkerRepository = WorkerRepositoryImpl()

    private val connection = isConnection(context = applicationContext)

    fun getMoviesViewModel(
        activity: MovieDetailsActivity? = null,
        fragment: Fragment? = null
    ): MoviesListViewModel? {
        activity?.let {
            return ViewModelProvider(
                activity,
                MoviesListViewModel.Factory(
                    repository = movieRepository,
                    isConnection = connection,
                    sharedPreferences = sharedPreferences
                )
            )[MoviesListViewModel::class.java]
        }
        fragment?.let {
            return ViewModelProvider(
                fragment,
                MoviesListViewModel.Factory(
                    repository = movieRepository,
                    isConnection = connection,
                    sharedPreferences = sharedPreferences
                )
            )[MoviesListViewModel::class.java]
        }
        return null

    }

    fun getMoviesDetailsRepository(fragment: Fragment): MovieDetailsViewModel {
        return ViewModelProvider(
            fragment,
            MovieDetailsViewModel.Factory(
                repository = movieDetailsRepository,
                isConnection = connection
            )
        )[MovieDetailsViewModel::class.java]
    }

    fun getMovieRepository() = movieRepository

    fun getSharedPreferences() = sharedPreferences

    fun getWorkerRepository() = workerRepository

    companion object {
        private const val SHARED_PREFERENCES_TAG = "MOVIE_CHOICE"
        const val TOKEN_CHOICE_MOVIE = "TOKEN_CHOICE_MOVIE"
    }

}