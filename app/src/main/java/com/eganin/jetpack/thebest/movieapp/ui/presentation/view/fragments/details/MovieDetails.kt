package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details

import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.details.MovieDetailsRepository
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details.header.Header
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details.info.MovieInfo
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details.list.Casts
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.MoviesListViewModel
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.utils.ShowSnackBar
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.BackgroundColor
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.MovieAppTheme
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.White

@Composable
fun MovieDetails(
    id: Int,
    repository: MovieDetailsRepository,
    connection: Boolean,
    scaffoldState: ScaffoldState,
    navController: NavController,
) {

    val movieDetailsViewModel: MovieDetailsViewModel = viewModel<MovieDetailsViewModel>(
        factory = MovieDetailsViewModel.Factory(
            repository = repository,
            isConnection = connection,
        )
    ).also { it.downloadDetailsData(id = id) }

    val movieDetailsData by movieDetailsViewModel.detailsData.observeAsState()
    val listActors by movieDetailsViewModel.castData.observeAsState()
    val dataCalendar by movieDetailsViewModel.dataCalendar.observeAsState()
    val loading by movieDetailsViewModel.loading
    val launchCalendar = remember { mutableStateOf(true) }

    if (dataCalendar != null && launchCalendar.value) {
        launchCalendar.value = !launchCalendar.value
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
                        adult = movieDetailsData?.adult ?: false,
                        imagePath = movieDetailsData?.backdropPath ?: "",
                        viewModel = movieDetailsViewModel,
                        movieInfo = it,
                        scaffoldState = scaffoldState,
                        navController = navController,
                    )
                }
            }
            item {
                MovieInfo(
                    title = movieDetailsData?.title,
                    tagLine = movieDetailsData?.genres?.joinToString(separator = ",") {
                        it.name ?: ""
                    },
                    rating = (movieDetailsData?.voteAverage?.div(2))?.toInt(),
                    countReviews = movieDetailsData?.voteCount,
                    description = movieDetailsData?.overview
                )
            }
            item { Casts(listActors = listActors ?: emptyList()) }
        }

        if (loading) {
            CircularProgressIndicator(
                color = White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun MovieDetailsPreview() {
    MovieAppTheme {
        //MovieDetails()
    }
}