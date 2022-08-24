package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.MoviesApi
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.Movie
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.*

@Composable
fun MovieBox(movie: Movie){
    Box {
        ImagePoster(imageUrlPath = movie.posterPath?:"")
        Text(
            text = if (movie.adult == true) "18+" else "12+",
            modifier = Modifier
                .background(AdultColor)
                .align(Alignment.TopStart)
                .padding(start = 10.dp, top = 12.dp),
            style = TextStyle(
                color = White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
            ),
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_like_unable),
            contentDescription = stringResource(
                id = R.string.like_description
            ),
            tint = UnableColor,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 13.dp, end = 8.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 6.dp)
                .align(Alignment.BottomStart)
        ) {
            Text(
                modifier = Modifier.padding(start = 2.dp),
                text = movie.genreIds.toString(),
                style = TextStyle(color = TagLineColor, fontSize = 8.sp),
            )
            Row(modifier = Modifier.padding(top = 4.dp)) {
                val vote = (movie.voteAverage?.div(2))?.toInt()
                (0..4).map {
                    if (it <= (vote ?: 0)){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star_unable_icon),
                            contentDescription = "$it star rating",
                            modifier = Modifier
                                .size(12.dp)
                                .padding(start = 2.dp),
                            tint = TagLineColor,
                        )
                    }else{
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star_unable_icon),
                            contentDescription = "$it star rating",
                            modifier = Modifier
                                .size(12.dp)
                                .padding(start = 2.dp),
                            tint = UnableColor,
                        )
                    }
                }
                Text(
                    text = "${movie.voteCount} REVIEWS",
                    style = TextStyle(
                        color = TopMenuColor,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
        }
    }
}

@Composable
fun ImagePoster(imageUrlPath :String){
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .drawWithCache {
                val gradient = Brush.verticalGradient(
                    colors = listOf(Color(0x00191926), Color(0xFF7F7F9E)),
                    startY = size.height / 3,
                    endY = size.height,
                    tileMode = TileMode.Decal
                )
                onDrawWithContent {
                    drawContent()
                    drawRect(gradient, blendMode = BlendMode.Multiply)
                }
            },
        model =(MoviesApi.BASE_IMAGE_URL + imageUrlPath),
        contentDescription = stringResource(id = R.string.poster_movie_description),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.ic_baseline_cloud_download_24),
        fallback = painterResource(R.drawable.ic_baseline_sms_failed_24)
    )
}