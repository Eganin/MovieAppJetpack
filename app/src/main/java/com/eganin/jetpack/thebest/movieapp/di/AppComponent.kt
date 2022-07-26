package com.eganin.jetpack.thebest.movieapp.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.eganin.jetpack.thebest.movieapp.data.models.repositories.MovieRepository
import com.eganin.jetpack.thebest.movieapp.presentation.view.fragments.list.MoviesListViewModel
import java.util.*


class AppComponent {

    private val movieRepository = MovieRepository(language = Locale.getDefault().language)

    fun getMoviesViewModel(fragment: Fragment): MoviesListViewModel {
        return ViewModelProvider(
            fragment,
            MoviesListViewModel.Factory(movieRepository)
        )[MoviesListViewModel::class.java]
    }

}