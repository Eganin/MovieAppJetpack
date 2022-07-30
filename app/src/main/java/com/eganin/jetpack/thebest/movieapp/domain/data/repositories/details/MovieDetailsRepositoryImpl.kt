package com.eganin.jetpack.thebest.movieapp.domain.data.repositories.details

import com.eganin.jetpack.thebest.movieapp.domain.data.database.MovieDatabase
import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.MovieDetailsEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.RetrofitModule
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.CreditsMovies
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.MovieDetailsResponse
import kotlinx.coroutines.withContext

class MovieDetailsRepositoryImpl(val language: String, database: MovieDatabase) :
    MovieDetailsRepository {

    private val movieDetailsDao =
        database.movieDetailsDao

    override suspend fun downloadDetailsInfoForMovie(movieId: Int): MovieDetailsResponse =
        withContext(defaultDispatcher) {
            RetrofitModule.api.getMovieDetailsUsingId(movieId = movieId, language = language)
        }

    override suspend fun downloadCredits(movieId: Int): CreditsMovies =
        withContext(defaultDispatcher) {
            RetrofitModule.api.getCreditsUsingId(movieId = movieId, language = language)
        }

    override suspend fun getAllInfoMovie(id: Long): MovieDetailsEntity =
        withContext(defaultDispatcher) {
            movieDetailsDao.getAllInfo(id = id)
        }

    override suspend fun insertMovieDetails(movie: MovieDetailsEntity) =
        withContext(defaultDispatcher) {
            movieDetailsDao.insertMovieDetails(movie = movie)
        }

    override suspend fun deleteAllInfoMovie() = withContext(defaultDispatcher) {
        movieDetailsDao.deleteAllInfoMovie()
    }
}