package com.eganin.jetpack.thebest.movieapp.domain.data.repositories.details

import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.MovieDetailsEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.CastItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.CreditsMovies
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.MovieDetailsResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface MovieDetailsRepository {
    val defaultDispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    suspend fun downloadDetailsInfoForMovie(movieId: Int): MovieDetailsResponse

    suspend fun downloadCredits(movieId: Int): CreditsMovies

    suspend fun getAllInfoMovie(id: Int): MovieDetailsEntity

    suspend fun insertMovieDetails(movie: MovieDetailsEntity)

    suspend fun deleteAllInfoMovie()

    suspend fun deleteInfoMovieById(id: Int)

    suspend fun getAllCredits(id: Int): List<CastItem>

    suspend fun insertCredits(credits: List<CastItem>)

    suspend fun deleteAlCredits()

    suspend fun deleteCreditsById(id: Int)
}