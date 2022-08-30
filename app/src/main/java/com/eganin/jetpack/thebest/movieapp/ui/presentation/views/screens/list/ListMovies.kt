package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
            ListViewState.Loading -> ListViewLoading()
            ListViewState.Display -> ListView(typeMovie = typeMovie, navController = navController)
            ListViewState.Error -> ListViewError()
            ListViewState.NoItems -> ListViewNoItems()
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.obtainEvent(event = ListViewState.Loading)
    }
}
