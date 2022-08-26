package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list.MovieCells
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.ui.theme.BackgroundColor
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.ui.theme.Black
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.ui.theme.White

@Composable
fun ListMoviesSearch(
    navController: NavController,
) {
    val viewModel =
        (LocalContext.current.applicationContext as MovieApp).myComponent.getMoviesListViewModel()

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
                style = TextStyle(fontSize = 14.sp, color = White),
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                color = White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

    }

}