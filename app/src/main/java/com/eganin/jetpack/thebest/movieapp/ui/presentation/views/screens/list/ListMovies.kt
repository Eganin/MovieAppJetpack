package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.domain.TypeObject
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list.*
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.list.models.ListViewState

@Composable
fun ListMovies(
    typeMovie: TypeMovies,
    navController: NavController,
) {
    TypeObject.type = typeMovie
    val viewModel = hiltViewModel<MoviesListViewModel>()
    val viewState = viewModel.listViewState.observeAsState()

    viewState.value?.let {
        when (it) {
            ListViewState.Loading -> ViewLoading()
            ListViewState.Display -> ListView(typeMovie = typeMovie, navController = navController)
            ListViewState.Error -> ViewError()
            ListViewState.NoItems -> ViewNoItems(textMessage = stringResource(R.string.no_items_label))
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.obtainEvent(event = ListViewState.Loading)
    }
}
