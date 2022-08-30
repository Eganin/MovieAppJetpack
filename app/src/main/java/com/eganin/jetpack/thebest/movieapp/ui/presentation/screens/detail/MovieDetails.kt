package com.eganin.jetpack.thebest.movieapp.ui.presentation.screens.detail

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.details.Details
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.details.MovieDetailsViewModel
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.details.models.DetailsState
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list.ViewError
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list.ViewLoading
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list.ViewNoItems

@Composable
fun MovieDetails(
    id: Int,
    scaffoldState: ScaffoldState,
    navController: NavController,
) {
    val viewModel = hiltViewModel<MovieDetailsViewModel>()
    val state = viewModel.detailsState

    state.value?.let {
        when (it) {
            is DetailsState.Loading -> ViewLoading()
            is DetailsState.Display -> Details(
                scaffoldState = scaffoldState,
                navController = navController
            )
            is DetailsState.Error -> ViewError()
            is DetailsState.NoInfo -> ViewNoItems(textMessage = stringResource(R.string.no_info_label))
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.obtainEvent(event = DetailsState.Loading(id = id))
    }
}
