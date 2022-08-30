package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eganin.jetpack.thebest.movieapp.application.TypeObject
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list.MovieCells
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list.MoviesListViewModel
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list.TopBarMovieList
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list.TypeMovies
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.JetMovieTheme
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.utils.ProgressBar
import javax.inject.Inject

@Composable
fun ListMovies(
    typeMovie: TypeMovies,
    navController: NavController,
) {
    TypeObject.type=typeMovie
    val viewModel = hiltViewModel<MoviesListViewModel>()

    val genresList by viewModel.genresData.observeAsState(emptyList())

    val state = viewModel.mainScreenState
    val dbState = viewModel.dbScreenState

    Column(
        modifier = Modifier
            .background(JetMovieTheme.colors.primaryBackground)
            .fillMaxSize()
    ) {
        TopBarMovieList(typeMovie = typeMovie.value)
        /*
        GridCells.Adaptive(170.dp)- делаем адаптивный Lazy Grid
         */
        LazyVerticalGrid(
            columns = GridCells.Adaptive(170.dp),
            modifier = Modifier.padding(top = 18.dp+JetMovieTheme.shapes.padding)
        ) {
            if (state.items.isEmpty()) {
                // с начала загружаем данные из БД
                items(dbState.items.size) { i ->
                    val item = dbState.items[i]
                    MovieCells(
                        movie = item,
                        genres = genresList,
                        navController = navController,
                    )
                }
            } else {
                // а потом подгружаем данные из интернета
                items(state.items.size) { i ->
                    val item = state.items[i]
                    if (i >= state.items.size - 1 && !state.endReached && !state.isLoading) {
                        viewModel.loadNextItems()
                    }
                    MovieCells(
                        movie = item,
                        genres = genresList,
                        navController = navController,
                    )
                }
            }
        }
        if (state.isLoading || dbState.isLoading) {
            ProgressBar()
        }
    }
}
