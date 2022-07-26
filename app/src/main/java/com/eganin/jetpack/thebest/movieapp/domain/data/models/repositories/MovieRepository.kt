package com.eganin.jetpack.thebest.movieapp.domain.data.models.repositories

import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.RetrofitModule
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.GenresItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.MovieResponse
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.TypeMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MovieRepository(val language: String) {

    private val defaultDispatcher = Dispatchers.IO

    suspend fun downloadMovies(page: Int, typeMovies: TypeMovies): MovieResponse =
        withContext(defaultDispatcher) {
            when (typeMovies) {
                TypeMovies.TOP_RATED -> RetrofitModule.api.getMoviesTopRated(
                    page = page,
                    language = language
                )
                TypeMovies.POPULAR -> RetrofitModule.api.getMoviesPopular(
                    page = page,
                    language = language
                )
                TypeMovies.NOW_PLAYING -> RetrofitModule.api.getMoviesNowPlaying(
                    page = page,
                    language = language
                )
                TypeMovies.UP_COMING -> RetrofitModule.api.getMoviesUpComing(
                    page = page,
                    language = language
                )
                TypeMovies.SEARCH -> TODO()
            }
        }

    suspend fun downloadGenres(): List<GenresItem> = withContext(defaultDispatcher){
        RetrofitModule.api.getGenres().genres
    }

}