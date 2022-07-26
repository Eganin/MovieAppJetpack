package com.eganin.jetpack.thebest.movieapp.presentation.view.fragments.list

import android.content.Context
import com.eganin.jetpack.thebest.movieapp.data.models.network.RetrofitModule
import com.eganin.jetpack.thebest.movieapp.data.models.network.entities.MovieResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MovieInteractor(private val dispatcher: CoroutineDispatcher, private val context: Context) {
    suspend fun downloadMoviesPopular(): MovieResponse = withContext(dispatcher) {
        RetrofitModule.api.getMoviesPopular(page = 1)
    }
}