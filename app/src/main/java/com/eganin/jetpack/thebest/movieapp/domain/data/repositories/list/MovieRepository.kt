package com.eganin.jetpack.thebest.movieapp.domain.data.repositories.list

import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.GenresItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.MovieResponse
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.TypeMovies
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface MovieRepository {

    val defaultDispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    suspend fun downloadMovies(page: Int, typeMovies: TypeMovies): MovieResponse

    suspend fun downloadSearchMovies(page: Int, query: String): MovieResponse

    suspend fun downloadGenres(): List<GenresItem>?
}