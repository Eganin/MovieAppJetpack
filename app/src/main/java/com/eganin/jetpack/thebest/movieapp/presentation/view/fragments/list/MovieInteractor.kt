package com.eganin.jetpack.thebest.movieapp.presentation.view.fragments.list

import android.content.Context
import com.eganin.jetpack.thebest.movieapp.data.models.network.RetrofitModule
import com.eganin.jetpack.thebest.movieapp.data.models.network.entities.MovieResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MovieInteractor(private val dispatcher: CoroutineDispatcher, private val context: Context) {
    suspend fun downloadMovies(page: Int, typeMovies: TypeMovies): MovieResponse =
        withContext(dispatcher) {
            when (typeMovies) {
                TypeMovies.TOP_RATED -> RetrofitModule.api.getMoviesTopRated(page = page)
                TypeMovies.POPULAR -> RetrofitModule.api.getMoviesPopular(page = page)
                TypeMovies.NOW_PLAYING -> RetrofitModule.api.getMoviesNowPlaying(page = page)
                TypeMovies.UP_COMING -> RetrofitModule.api.getMoviesUpComing(page = page)
                TypeMovies.SEARCH -> TODO()
            }
        }
}