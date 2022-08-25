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
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.ui.theme.TagLineColor
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.ui.theme.UnableColor
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.ui.theme.White

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
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        style = TextStyle(color = White, fontSize = 40.sp, fontWeight = FontWeight.Bold)
    )
}

@Composable
fun TagLine(tags: String) {
    Text(
        text = tags,
        style = TextStyle(color = TagLineColor),
        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
    )
}

@Composable
fun RatingBar(rating: Int) {
    val modifierStars = Modifier
        .padding(start = 6.dp, top = 8.dp)
        .height(12.dp)
    Row(modifier = Modifier.padding(start = 10.dp, top = 8.dp)) {
        (0..4).map { i ->
            Icon(
                painter = painterResource(id = R.drawable.ic_star_unable_icon),
                contentDescription = "$i Star Rating",
                tint = if (i >= rating) UnableColor else TagLineColor,
                modifier = modifierStars
            )
        }
    }
}

@Composable
fun CounterReviews(countReviews: Int) {
    Text(
        text = "$countReviews REVIEWS",
        modifier = Modifier.padding(12.dp),
        style = TextStyle(color = UnableColor, fontWeight = FontWeight.Bold)
    )
}

@Composable
fun StoryLine(description: String) {
    Column(
        modifier = Modifier
            .padding(top = 23.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "StoryLine",
            style = TextStyle(color = White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        )
        Text(
            modifier = Modifier.padding(top = 15.dp),
            text = description,
            style = TextStyle(color = UnableColor, fontSize = 14.sp)
        )
    }
}