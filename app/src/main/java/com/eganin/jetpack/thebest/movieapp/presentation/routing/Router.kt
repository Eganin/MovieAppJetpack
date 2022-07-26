package com.eganin.jetpack.thebest.movieapp.presentation.routing

import com.eganin.jetpack.thebest.movieapp.data.models.entities.Movie

interface Router {
    fun openMovieList()
    fun openMovieDetails(movieDetails: Movie)
}