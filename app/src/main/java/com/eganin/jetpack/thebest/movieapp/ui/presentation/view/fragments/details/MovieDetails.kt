package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details

import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.details.MovieDetailsRepository
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details.header.Header
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details.info.MovieInfo
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details.list.Casts
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.BackgroundColor
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.MovieAppTheme

@Composable
fun MovieDetails(
    id: Int, repository: MovieDetailsRepository, connection: Boolean,
    scaffoldState: ScaffoldState
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

    if (dataCalendar != null){
        dataCalendar!!.flags = FLAG_ACTIVITY_NEW_TASK
        LocalContext.current.applicationContext.startActivity(dataCalendar)
    }

    LazyColumn(
        modifier = Modifier
            .background(BackgroundColor)
            .fillMaxSize()
    ) {
        item {
            movieDetailsData?.let {
                Header(
                    adult = movieDetailsData?.adult ?: false,
                    imagePath = movieDetailsData?.backdropPath ?: "",
                    viewModel = movieDetailsViewModel,
                    movieInfo = it,
                    scaffoldState=scaffoldState
                )
            }
        }
        item {
            MovieInfo(
                title = movieDetailsData?.title,
                tagLine = movieDetailsData?.genres?.joinToString(separator = ",") { it.name ?: "" },
                rating = (movieDetailsData?.voteAverage?.div(2))?.toInt(),
                countReviews = movieDetailsData?.voteCount,
                description = movieDetailsData?.overview
            )
        }
        item { Casts(listActors = listActors ?: emptyList()) }
    }
}


@Composable
@Preview(showBackground = true)
fun MovieDetailsPreview() {
    MovieAppTheme {
        //MovieDetails()
    }
}