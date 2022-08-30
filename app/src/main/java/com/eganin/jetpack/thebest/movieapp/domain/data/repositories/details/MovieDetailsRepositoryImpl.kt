package com.eganin.jetpack.thebest.movieapp.domain.data.repositories.details

import com.eganin.jetpack.thebest.movieapp.domain.data.database.MovieDatabase
import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.MovieDetailsEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.CastItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.CreditsMovies
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.MovieDetailsResponse
import com.eganin.jetpack.thebest.movieapp.domain.data.network.RetrofitModule
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class MovieDetailsRepositoryImpl @Inject constructor(
    @Named("language") val language: String,
    database: MovieDatabase
) :
    MovieDetailsRepository {

    private val movieDetailsDao = database.movieDetailsDao

    private val creditsDao = database.creditsDao

    // загружаем детальную информацию о фильме по id
    override suspend fun downloadDetailsInfoForMovie(movieId: Int): MovieDetailsResponse? =
        withContext(defaultDispatcher) {
            RetrofitModule.api.getMovieDetailsUsingId(movieId = movieId, language = language)
        }

    // загружаем список актероа по id
    override suspend fun downloadCredits(movieId: Int): CreditsMovies =
        withContext(defaultDispatcher) {
            RetrofitModule.api.getCreditsUsingId(movieId = movieId, language = language)
        }

    // получаем данные о фильме
    override suspend fun getAllInfoMovie(id: Int): MovieDetailsEntity? =
        withContext(defaultDispatcher) {
            movieDetailsDao.getAllInfo(id = id)
        }

    // вставляем в БД информацию о фильме
    override suspend fun insertMovieDetails(movie: MovieDetailsEntity) =
        withContext(defaultDispatcher) {
            movieDetailsDao.insertMovieDetails(movie = movie)
        }

    // удаляем все доп информацию о всех фильмах
    override suspend fun deleteAllInfoMovie() = withContext(defaultDispatcher) {
        movieDetailsDao.deleteAllInfoMovie()
    }

    // удаляем доп инфу фильма по id
    override suspend fun deleteInfoMovieById(id: Int) = withContext(defaultDispatcher) {
        movieDetailsDao.deleteInfoMovieById(id = id)
    }

    // получаем всех актеров определенного фильма, по id фильма
    override suspend fun getAllCredits(id: Int) = withContext(defaultDispatcher) {
        creditsDao.getAllCredits(id = id)
    }

    override suspend fun insertCredits(credits: List<CastItem>) = withContext(defaultDispatcher) {
        creditsDao.insertCredits(credits = credits)
    }

    override suspend fun deleteAllCredits() = withContext(defaultDispatcher) {
        creditsDao.deleteAllCredits()
    }

    override suspend fun deleteCreditsById(id: Int) = withContext(defaultDispatcher) {
        creditsDao.deleteCreditsById(id = id)
    }
}