package com.eganin.jetpack.thebest.movieapp.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.eganin.jetpack.thebest.movieapp.domain.data.database.MovieDatabase
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.details.MovieDetailsRepositoryImpl
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.list.MovieRepositoryImpl
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details.MovieDetailsViewModel
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.MoviesListViewModel
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.MovieDetailsActivity
import java.util.*


class AppComponent(applicationContext: Context) {

    private val defaultLanguage = Locale.getDefault().language
    private val movieRepository =
        MovieRepositoryImpl(language = defaultLanguage, applicationContext = applicationContext)
    private val movieDetailsRepository = MovieDetailsRepositoryImpl(
        language = defaultLanguage,
        applicationContext = applicationContext
    )
    val database = MovieDatabase.create(applicationContext)

    fun getMoviesViewModel(fragment: Fragment): MoviesListViewModel {
        return ViewModelProvider(
            fragment,
            MoviesListViewModel.Factory(movieRepository)
        )[MoviesListViewModel::class.java]
    }

    fun getMoviesViewModelForActivity(activity: MovieDetailsActivity): MoviesListViewModel {
        return ViewModelProvider(
            activity,
            MoviesListViewModel.Factory(movieRepository)
        )[MoviesListViewModel::class.java]
    }

    fun getMoviesDetailsRepository(fragment: Fragment): MovieDetailsViewModel {
        return ViewModelProvider(
            fragment,
            MovieDetailsViewModel.Factory(movieDetailsRepository)
        )[MovieDetailsViewModel::class.java]
    }

}