package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.details

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.details.header.Header
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.details.info.MovieInfo
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.details.list.Casts
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.JetMovieTheme

@Composable
fun Details(
    scaffoldState: ScaffoldState,
    navController: NavController,
) {

    val viewModel = hiltViewModel<MovieDetailsViewModel>()

    val movieDetailsData by viewModel.detailsData.observeAsState()
    val listActors by viewModel.castData.observeAsState(emptyList())
    // LiveData с Intent
    val dataCalendar by viewModel.dataCalendar.observeAsState()

    if (dataCalendar != null) {
        // Запуск activity calendar
        dataCalendar!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        LocalContext.current.applicationContext.startActivity(dataCalendar)
    }

    Column(
        modifier = Modifier
            .background(JetMovieTheme.colors.primaryBackground)
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
                        countReviews = it.voteCount,
                        description = it.overview,
                    )
                }
            }
            item { Casts(listActors = listActors) }
        }
    }
}