package com.eganin.jetpack.thebest.movieapp.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eganin.jetpack.thebest.movieapp.fragments.details.MovieDetailsViewModel
import com.eganin.jetpack.thebest.movieapp.fragments.list.MovieInteractor
import com.eganin.jetpack.thebest.movieapp.fragments.list.MoviesListViewModel
import kotlinx.coroutines.Dispatchers

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    private val defaultDispatchers = Dispatchers.IO

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        when (modelClass) {
            MovieDetailsViewModel::class.java -> MovieDetailsViewModel()
            MoviesListViewModel::class.java -> MoviesListViewModel(
                interactor = MovieInteractor(
                    dispatcher = defaultDispatchers,
                    context = context,
                )
            )
            else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
        } as T
}