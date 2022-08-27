package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.details.info

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.JetMovieTheme
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.TagLineColor
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.UnableColor
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.White

@Composable
fun MovieInfo(
    title: String,
    tagLine: String,
    rating: Int,
    countReviews: Int,
    description: String
) {
    Title(title = title)
    TagLine(tags = tagLine)
    Row {
        RatingBar(rating = rating)
        CounterReviews(countReviews = countReviews)
    }
    StoryLine(description = description)
}

@Composable
fun Title(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .padding(
                start = 16.dp + JetMovieTheme.shapes.padding,
                end = 16.dp + JetMovieTheme.shapes.padding
            )
            .fillMaxWidth(),
        color = JetMovieTheme.colors.primaryText,
        style = JetMovieTheme.typography.heading,
    )
}

@Composable
fun TagLine(tags: String) {
    Text(
        text = tags,
        style = JetMovieTheme.typography.body,
        color = JetMovieTheme.colors.tintColor,
        modifier = Modifier.padding(
            start = 16.dp + JetMovieTheme.shapes.padding,
            top = 4.dp + JetMovieTheme.shapes.padding
        )
    )
}

@Composable
fun RatingBar(rating: Int) {
    val modifierStars = Modifier
        .padding(
            start = 6.dp + JetMovieTheme.shapes.padding,
            top = 8.dp + JetMovieTheme.shapes.padding
        )
        .height(12.dp + JetMovieTheme.shapes.padding)
    Row(
        modifier = Modifier.padding(
            start = 10.dp + JetMovieTheme.shapes.padding,
            top = 8.dp + JetMovieTheme.shapes.padding
        )
    ) {
        (0..4).map { i ->
            Icon(
                painter = painterResource(id = R.drawable.ic_star_unable_icon),
                contentDescription = "$i Star Rating",
                tint =
                if (i >= rating) JetMovieTheme.colors.secondaryText
                else JetMovieTheme.colors.tintColor,
                modifier = modifierStars
            )
        }
    }
}

@Composable
fun CounterReviews(countReviews: Int) {
    Text(
        text = "$countReviews REVIEWS",
        modifier = Modifier.padding(12.dp + JetMovieTheme.shapes.padding),
        color = JetMovieTheme.colors.secondaryText,
        style = JetMovieTheme.typography.caption,
    )
}

@Composable
fun StoryLine(description: String) {
    Column(
        modifier = Modifier
            .padding(
                top = 23.dp + JetMovieTheme.shapes.padding,
                start = 16.dp + JetMovieTheme.shapes.padding,
                end = 16.dp + JetMovieTheme.shapes.padding
            )
            .fillMaxWidth()
    ) {
        Text(
            text = "StoryLine",
            color = JetMovieTheme.colors.primaryText,
            style = JetMovieTheme.typography.caption,
        )
        Text(
            modifier = Modifier.padding(top = 15.dp + JetMovieTheme.shapes.padding),
            text = description,
            color = JetMovieTheme.colors.secondaryText,
            style = JetMovieTheme.typography.body,
        )
    }
}