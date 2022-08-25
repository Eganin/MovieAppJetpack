package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.detail

import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.details.header.Header
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.details.info.MovieInfo
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.details.list.Casts
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.ui.theme.BackgroundColor
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.ui.theme.MovieAppTheme
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.ui.theme.White

@Composable
fun MovieDetails(
    id: Int,
    scaffoldState: ScaffoldState,
    navController: NavController,
) {
    val viewModel =
        (LocalContext.current.applicationContext as MovieApp).myComponent.getMovieDetailsViewModel()

    LaunchedEffect(viewModel) {
        viewModel.downloadDetailsData(id = id)
    }

    val movieDetailsData by viewModel.detailsData.observeAsState()
    val listActors by viewModel.castData.observeAsState(emptyList())
    // LiveData с Intent
    val dataCalendar by viewModel.dataCalendar.observeAsState()
    val loading by viewModel.loading

    if (dataCalendar != null) {
        // Запуск activity calendar
        dataCalendar!!.flags = FLAG_ACTIVITY_NEW_TASK
        LocalContext.current.applicationContext.startActivity(dataCalendar)
    }

    Column(
        modifier = Modifier
            .background(BackgroundColor)
            .fillMaxSize()
    ) {
        LazyColumn {
            item {
                movieDetailsData?.let {
                    Header(
                        adult = it.adult,
                        imagePath = it.backdropPath,
                        movieInfo = it,
                        scaffoldState = scaffoldState,
                        navController = navController,
                    )
                }
            }
            item {
                movieDetailsData?.let {
                    MovieInfo(
                        title = it.title,
                        tagLine = it.genres.joinToString(separator = ",") { it.name },
                        rating = (it.voteAverage / 2).toInt(),
                        countReviews = it.voteCount ,
                        description = it.overview ,
                    )
                }
            }
            item { Casts(listActors = listActors) }
        }

        if (loading) {
            CircularProgressIndicator(
                color = White,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }
    }
}
