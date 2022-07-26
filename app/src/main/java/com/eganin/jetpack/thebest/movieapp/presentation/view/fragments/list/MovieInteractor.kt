package com.eganin.jetpack.thebest.movieapp.presentation.view.fragments.list

import android.content.Context
import com.eganin.jetpack.thebest.movieapp.data.models.entities.Movie
import com.eganin.jetpack.thebest.movieapp.data.models.loadMovies
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MovieInteractor(private val dispatcher: CoroutineDispatcher, private val context: Context) {
    suspend fun downloadMoviesList(): List<Movie> = withContext(dispatcher) {
        loadMovies(context = context)
    }
}