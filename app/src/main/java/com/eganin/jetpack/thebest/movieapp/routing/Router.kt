package com.eganin.jetpack.thebest.movieapp.routing

import android.view.View
import com.eganin.jetpack.thebest.movieapp.data.models.Movie

interface Router {
    fun openMovieList()
    fun openMovieDetails(movieDetails: Movie)
}