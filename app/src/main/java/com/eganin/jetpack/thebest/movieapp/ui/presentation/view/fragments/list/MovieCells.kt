package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.Movie
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.MovieBox
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.BackgroundColor
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.TimeLineColor
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.TopMenuColor
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.White

@Composable
fun MovieCells(movie: Movie) {
    Card(
        modifier = Modifier
            .width(170.dp)
            .fillMaxHeight()
            .padding(16.dp),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, TopMenuColor),
        backgroundColor = BackgroundColor
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            MovieBox(movie = movie)
            Text(
                text = movie.title ?: "",
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