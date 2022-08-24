package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.MoviesApi
import com.eganin.jetpack.thebest.movieapp.domain.data.notifications.MovieNotificationsManager
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.list.MovieRepository
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.utils.CircularIndeterminateProgressBar
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.utils.ShowSnackBar
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.*

@Composable
fun ListMovies(
    repository: MovieRepository,
    isConnection: Boolean,
    sharedPreferences: SharedPreferences,
    notificationsManager: MovieNotificationsManager,
    typeMovie: TypeMovies,
    navController: NavController,
) {
    val viewModel = viewModel<MoviesListViewModel>(
        factory = MoviesListViewModel.Factory(
            repository = repository,
            isConnection = isConnection,
            sharedPreferences = sharedPreferences,
            notificationsManager = notificationsManager,
        )
    )

    viewModel.changeTypeMovies(type = typeMovie)
    viewModel.download()
    val movies by viewModel.moviesData.observeAsState()
    val genresList by viewModel.genresData.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .background(BackgroundColor)
            .fillMaxSize()
    ) {
        TopBarMovieList(typeMovie = typeMovie.value)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(170.dp),
            modifier = Modifier.padding(top = 18.dp)
        ) {
            movies?.map {
                item {
                    MovieCells(movie = it, genres = genresList, navController = navController)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Preview
fun PreviewListMovie() {
    MovieAppTheme {
        Scaffold {
            //ListMovies()
        }
    }
}