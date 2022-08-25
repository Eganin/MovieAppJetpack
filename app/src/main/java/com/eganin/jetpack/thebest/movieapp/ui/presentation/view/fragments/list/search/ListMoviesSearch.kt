package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.search

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.domain.data.notifications.MovieNotificationsManager
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.list.MovieRepository
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.MovieCells
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.MoviesListViewModel
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.BackgroundColor
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.Black
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.White

@Composable
fun ListMoviesSearch(
    repository: MovieRepository,
    notificationsManager: MovieNotificationsManager,
    navController: NavController,
) {

    val viewModel = viewModel<MoviesListViewModel>(
        factory = MoviesListViewModel.Factory(
            repository = repository,
            notificationsManager = notificationsManager,
        )
    )

    val movies by viewModel.moviesData.observeAsState()
    val genresList by viewModel.genresData.observeAsState(emptyList())
    val loading by viewModel.loading

    var text by remember { mutableStateOf("") }

    val trailingIconView = @Composable {
        IconButton(
            onClick = {
                viewModel.downloadSearch(query = text)
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_search_24),
                contentDescription = "",
                tint = Black
            )
        }
    }

    Column(
        modifier = Modifier
            .background(BackgroundColor)
            .fillMaxSize()
    ) {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            value = text,
            onValueChange = {
                text = it
            },
            label = { Text("Search Movie") },
            placeholder = { Text(text = "Enter Text") },
            trailingIcon = trailingIconView,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = White,
                cursorColor = Black,
                focusedLabelColor = Black,
                focusedIndicatorColor = Black,
            ),
            shape = RoundedCornerShape(20.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(170.dp),
        ) {
            movies?.map {
                item {
                    MovieCells(
                        movie = it,
                        genres = genresList,
                        navController = navController,
                        viewModel = viewModel,
                    )
                }
            }
        }

        if (text.isEmpty()) {
            Text(
                text = stringResource(id = R.string.start_typing_in_a_search_bar_to_find_a_movie),
                style = TextStyle(fontSize = 14.sp, color = White),
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }

        if (loading) {
            CircularProgressIndicator(
                color = White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

    }
}