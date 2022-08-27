package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.details.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.domain.data.network.MoviesApi
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.CastItem
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.JetMovieTheme


@Composable
fun Casts(listActors: List<CastItem>) {
    Column(
        modifier = Modifier
            .padding(
                start = 16.dp + JetMovieTheme.shapes.padding,
                end = 16.dp + JetMovieTheme.shapes.padding,
                top = 24.dp + JetMovieTheme.shapes.padding
            )
            .fillMaxWidth()
    ) {
        Text(
            text = "Cast",
            color=JetMovieTheme.colors.primaryText,
            style = JetMovieTheme.typography.caption,
        )
        ListActors(listActors = listActors)
    }
}

@Composable
private fun ListActors(listActors: List<CastItem>) {
    LazyRow {
        listActors.map { actorInfo ->
            item {
                ActorCell(info = actorInfo)
            }
        }
    }
}

@Composable
private fun ActorCell(info: CastItem) {
    Card(
        shape = JetMovieTheme.shapes.cornersStyle,
        backgroundColor = JetMovieTheme.colors.primaryBackground,
        modifier = Modifier.padding(8.dp+ JetMovieTheme.shapes.padding)
    ) {
        Column {
            AsyncImage(
                modifier = Modifier.size(80.dp),
                model = (MoviesApi.BASE_IMAGE_URL + info.profilePath),
                contentDescription = stringResource(id = R.string.image_actor_description),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_baseline_cloud_download_24),
                fallback = painterResource(R.drawable.ic_baseline_sms_failed_24)
            )
            Text(
                text = info.name,
                color = JetMovieTheme.colors.primaryText,
                modifier = Modifier
                    .width(80.dp+ JetMovieTheme.shapes.padding)
                    .padding(bottom = 50.dp+ JetMovieTheme.shapes.padding)
            )
        }
    }
}