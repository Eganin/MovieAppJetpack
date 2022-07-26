package com.eganin.jetpack.thebest.movieapp.ui.presentation.routing

interface Router {
    fun openMovieList()
    fun openMovieDetails(movieId : Int)
}