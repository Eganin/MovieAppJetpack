package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.GenresItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.Movie
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.*

@Composable
fun MovieCells(
    movie: Movie,
    genres: List<GenresItem>,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .width(170.dp)
            .fillMaxHeight()
            .padding(16.dp + JetMovieTheme.shapes.padding)
            .clickable {
                with(navController) {
                    //кладем id и навигируемся на MovieDetails
                    currentBackStackEntry?.savedStateHandle?.set(key = "ID_KEY", value = movie.id)
                    navigate("details")
                }
            },
        shape = JetMovieTheme.shapes.cornersStyle,
        border = BorderStroke(width = 1.dp, JetMovieTheme.colors.secondaryText),
        backgroundColor = JetMovieTheme.colors.cardBackground,
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            MovieBox(movie = movie, genres = genres)
            Text(
                text = movie.title,
                color = JetMovieTheme.colors.primaryText,
                style = JetMovieTheme.typography.caption,
                modifier = Modifier.padding(
                    start = 8.dp + JetMovieTheme.shapes.padding,
                    top = 8.dp + JetMovieTheme.shapes.padding
                )
            )
            Text(
                text = "${movie.voteCount} REVIEWS",
                color = JetMovieTheme.colors.secondaryText,
                style = JetMovieTheme.typography.body,
                modifier = Modifier.padding(8.dp + JetMovieTheme.shapes.padding)
            )
        }
    }
}