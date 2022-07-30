package com.eganin.jetpack.thebest.movieapp.domain.data.repositories.details

import android.content.Context
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.RetrofitModule
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.CreditsMovies
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.MovieDetailsResponse
import kotlinx.coroutines.withContext

class MovieDetailsRepositoryImpl(val language: String,applicationContext: Context) : MovieDetailsRepository {

    private val database = (applicationContext as MovieApp).myComponent.database

    override suspend fun downloadDetailsInfoForMovie(movieId: Int): MovieDetailsResponse =
        withContext(defaultDispatcher) {
            RetrofitModule.api.getMovieDetailsUsingId(movieId = movieId, language = language)
        }

    override suspend fun downloadCredits(movieId: Int): CreditsMovies = withContext(defaultDispatcher) {
        RetrofitModule.api.getCreditsUsingId(movieId = movieId, language = language)
    }
}