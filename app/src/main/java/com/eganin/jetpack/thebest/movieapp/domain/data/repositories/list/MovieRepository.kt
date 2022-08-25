package com.eganin.jetpack.thebest.movieapp.domain.data.repositories.list

import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.FavouriteEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.MovieEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.GenresItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.MovieResponse
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list.TypeMovies
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface MovieRepository {

    val defaultDispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    suspend fun downloadMovies(page: Int, typeMovies: TypeMovies): MovieResponse

    suspend fun downloadSearchMovies(page: Int, query: String): MovieResponse

    suspend fun downloadGenres(): List<GenresItem>

    suspend fun getAllMovies() : List<MovieEntity>

    suspend fun insertMovies(movies: List<MovieEntity>)

    suspend fun deleteAllMovies()

    suspend fun getFavouriteMovieUsingID(id: Int): FavouriteEntity?

    suspend fun insertFavouriteMovie(favouriteMovie: FavouriteEntity)

    suspend fun deleteAllFavouriteMovie()

    suspend fun deleteFavouriteMovieUsingID(id: Int)
}