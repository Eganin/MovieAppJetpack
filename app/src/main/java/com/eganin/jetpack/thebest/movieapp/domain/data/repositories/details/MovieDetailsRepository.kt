package com.eganin.jetpack.thebest.movieapp.domain.data.repositories.details

import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.MovieDetailsEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.CreditsMovies
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.MovieDetailsResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface MovieDetailsRepository {
    val defaultDispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    suspend fun downloadDetailsInfoForMovie(movieId: Int): MovieDetailsResponse

    suspend fun downloadCredits(movieId: Int): CreditsMovies

    suspend fun getAllInfoMovie(id:Long): MovieDetailsEntity

    suspend fun insertMovieDetails(movie: MovieDetailsEntity)

    suspend fun deleteAllInfoMovie()
}