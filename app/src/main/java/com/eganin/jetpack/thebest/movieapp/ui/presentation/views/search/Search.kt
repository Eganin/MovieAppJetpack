package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list.MovieCells
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list.MoviesListViewModel
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.EditTextSearchBackGround
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.JetMovieTheme

@Composable
fun Search(navController: NavController) {
    val viewModel = hiltViewModel<MoviesListViewModel>()

    val genresList by viewModel.genresData.observeAsState(emptyList())
    val state = viewModel.searchScreenState

    var text by remember { mutableStateOf("") }

    val trailingIconView = @Composable {
        IconButton(
            onClick = {
                viewModel.loadSearchItems(query = text)
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_search_24),
                contentDescription = "",
                tint = JetMovieTheme.colors.tintColor,
            )
        }
    }

    Column(
        modifier = Modifier
            .background(JetMovieTheme.colors.primaryBackground)
            .fillMaxSize()
    ) {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp + JetMovieTheme.shapes.padding),
            value = text,
            onValueChange = {
                text = it
            },
            label = { Text("Search Movie") },
            placeholder = { Text(text = "Enter Text") },
            trailingIcon = trailingIconView,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = EditTextSearchBackGround,
                cursorColor = JetMovieTheme.colors.tintColor,
                focusedLabelColor = JetMovieTheme.colors.tintColor,
                focusedIndicatorColor = JetMovieTheme.colors.tintColor,
            ),
            shape = JetMovieTheme.shapes.cornersStyle,
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(170.dp),
        ) {
            items(state.items.size) { i ->
                val item = state.items[i]
                if (i >= state.items.size - 1 && !state.endReached && !state.isLoading) {
                    viewModel.loadSearchItems(query = text)
                }
                MovieCells(
                    movie = item,
                    genres = genresList,
                    navController = navController,
                )
            }
        }

        if (text.isEmpty()) {
            Text(
                text = stringResource(id = R.string.start_typing_in_a_search_bar_to_find_a_movie),
                style = JetMovieTheme.typography.body,
                color = JetMovieTheme.colors.primaryText,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }

    }
}