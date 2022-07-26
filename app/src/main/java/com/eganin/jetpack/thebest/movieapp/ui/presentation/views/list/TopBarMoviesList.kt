package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.JetMovieTheme


@Composable
fun TopBarMovieList(typeMovie: String) {
    Row {
        Icon(
            painter = painterResource(id = R.drawable.ic_combined_shape),
            tint = JetMovieTheme.colors.tintColor,
            contentDescription = "",
            modifier = Modifier
                .size(40.dp + JetMovieTheme.shapes.padding)
                .padding(
                    start = 16.dp + JetMovieTheme.shapes.padding,
                    top = 18.dp + JetMovieTheme.shapes.padding
                )
        )
        Text(
            text = typeMovie,
            modifier = Modifier.padding(
                top = 19.dp + JetMovieTheme.shapes.padding,
                start = 12.dp + JetMovieTheme.shapes.padding
            ),
            style = JetMovieTheme.typography.caption,
            color= JetMovieTheme.colors.primaryText,
        )
    }
}