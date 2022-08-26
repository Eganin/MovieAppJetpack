package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.GenresItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.Movie
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.ui.theme.BackgroundColor
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.ui.theme.TimeLineColor
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.ui.theme.TopMenuColor
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.ui.theme.White

@Composable
fun MovieCells(
    movie: Movie,
    genres: List<GenresItem>,
    navController: NavController,
    typeMovies: TypeMovies
) {
    Card(
        modifier = Modifier
            .width(170.dp)
            .fillMaxHeight()
            .padding(16.dp)
            .clickable {
                with(navController) {
                    //кладем id и навигируемся на MovieDetails
                    currentBackStackEntry?.savedStateHandle?.set(key = "ID_KEY", value = movie.id)
                    navigate("details")
                }
            },
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, TopMenuColor),
        backgroundColor = BackgroundColor
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            MovieBox(movie = movie, genres = genres,typeMovies=typeMovies)
            Text(
                text = movie.title,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp, color = White),
                modifier = Modifier.padding(start = 8.dp, top = 8.dp)
            )
            Text(
                text = "${movie.voteCount} REVIEWS",
                style = TextStyle(color = TimeLineColor, fontSize = 8.sp),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}