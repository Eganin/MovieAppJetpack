package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.MoviesApi
import com.eganin.jetpack.thebest.movieapp.domain.data.notifications.MovieNotificationsManager
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.list.MovieRepository
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.*

@Composable
fun ListMovies(
    repository: MovieRepository,
    isConnection: Boolean,
    sharedPreferences: SharedPreferences,
    notificationsManager: MovieNotificationsManager,
) {

    val viewModel = viewModel<MoviesListViewModel>(
        factory = MoviesListViewModel.Factory(
            repository = repository,
            isConnection = isConnection,
            sharedPreferences = sharedPreferences,
            notificationsManager = notificationsManager,
        )
    ).also { it.downloadMovies() }

    val movies by viewModel.moviesData.observeAsState()
    val typeMovie by viewModel.changeMovies.observeAsState("Popular")

    Column(
        modifier = Modifier
            .background(BackgroundColor)
            .fillMaxSize()
    ) {
        TopBarMovieList(typeMovie = typeMovie)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(170.dp),
            modifier = Modifier.padding(top = 18.dp)
        ) {
            movies?.map {
                item {
                    MovieCells(movie = it)
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