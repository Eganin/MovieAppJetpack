package com.eganin.jetpack.thebest.movieapp.domain.data.repositories.list

import android.content.Context
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.MovieEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.RetrofitModule
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.GenresItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.MovieResponse
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.TypeMovies
import kotlinx.coroutines.withContext


class MovieRepositoryImpl(val language: String, applicationContext: Context) : MovieRepository {

    private val movieDao = (applicationContext as MovieApp).myComponent.database.movieDao

    override suspend fun downloadMovies(page: Int, typeMovies: TypeMovies): MovieResponse =
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
                else -> RetrofitModule.api.getMoviesUpComing(
                    page = page,
                    language = language
                )
            }
        }

    override suspend fun downloadSearchMovies(page: Int, query: String): MovieResponse =
        withContext(defaultDispatcher) {
            RetrofitModule.api.getSearchMovie(queryText = query, page = page)
        }

    override suspend fun downloadGenres(): List<GenresItem>? = withContext(defaultDispatcher) {
        RetrofitModule.api.getGenres().genres
    }

    override suspend fun getAllMovies(): List<MovieEntity> = withContext(defaultDispatcher) {
        movieDao.getAllMovies()
    }

    override suspend fun insertMovies(movies: List<MovieEntity>) = withContext(defaultDispatcher) {
        movieDao.insertMovies(movies = movies)
    }

    override suspend fun deleteAllMovies() = withContext(defaultDispatcher) {
        movieDao.deleteAllMovies()
    }

}