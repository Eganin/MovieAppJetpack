package com.eganin.jetpack.thebest.movieapp.ui.presentation.screens.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.domain.TypeObject
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list.*
import com.eganin.jetpack.thebest.movieapp.ui.presentation.screens.list.models.ListViewState
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.search.Search

@Composable
fun ListMovies(
    typeMovie: TypeMovies=TypeMovies.POPULAR,
    navController: NavController,
    isSearchPage: Boolean=false,
) {
    TypeObject.type = typeMovie
    val viewModel = hiltViewModel<MoviesListViewModel>()
    val viewState = viewModel.listViewState.observeAsState()

    viewState.value?.let {
        when (it) {
            ListViewState.Loading -> ViewLoading()
            ListViewState.Display ->
                if (!isSearchPage)
                    ListView(
                        typeMovie = typeMovie,
                        navController = navController
                    ) else
                    Search(navController = navController)
            ListViewState.Error -> ViewError()
            ListViewState.NoItems -> ViewNoItems(textMessage = stringResource(R.string.no_items_label))
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.obtainEvent(event = ListViewState.Loading)
    }
}
