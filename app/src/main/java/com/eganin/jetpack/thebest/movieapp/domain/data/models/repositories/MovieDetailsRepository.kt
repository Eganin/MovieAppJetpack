package com.eganin.jetpack.thebest.movieapp.domain.data.models.repositories

import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.RetrofitModule
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.CreditsMovies
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.MovieDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDetailsRepository(val language: String) {

    private val defaultDispatcher = Dispatchers.IO

    suspend fun downloadDetailsInfoForMovie(movieId: Int): MovieDetailsResponse =
        withContext(defaultDispatcher) {
            RetrofitModule.api.getMovieDetailsUsingId(movieId = movieId, language = language)
        }

    suspend fun downloadCredits(movieId: Int): CreditsMovies = withContext(defaultDispatcher) {
        RetrofitModule.api.getCreditsUsingId(movieId = movieId, language = language)
    }
}