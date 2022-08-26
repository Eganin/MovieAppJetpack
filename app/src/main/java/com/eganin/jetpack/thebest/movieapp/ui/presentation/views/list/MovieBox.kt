package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.GenresItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.Movie
import com.eganin.jetpack.thebest.movieapp.domain.data.network.MoviesApi
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.ui.theme.*

@Composable
fun MovieBox(movie: Movie, genres: List<GenresItem>,typeMovies: TypeMovies) {

    val viewModel =
        (LocalContext.current.applicationContext as MovieApp).myComponent.getMoviesListViewModel(typeMovies = typeMovies)
    // isFavouriteImage - для отслеживания сотояния фильма из БД
    val isFavouriteMovie = remember { mutableStateOf(false) }
    val id = remember { mutableStateOf(0) }

    LaunchedEffect(viewModel) {
        val res = viewModel.existsMovie(id = movie.id)
        isFavouriteMovie.value = res.first
        id.value = res.second
    }

    Box {
        ImagePoster(imageUrlPath = movie.posterPath )
        Card(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 10.dp, top = 12.dp)
        ) {
            Text(
                text = if (movie.adult) "18+" else "12+",
                modifier = Modifier
                    .background(AdultColor),
                style = TextStyle(
                    color = White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                ),
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_like_unable),
            contentDescription = stringResource(
                id = R.string.like_description
            ),
            tint = if (isFavouriteMovie.value && id.value == movie.id) TagLineColor else UnableColor,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 13.dp, end = 8.dp)
                .clickable {
                    viewModel.usingDBFavouriteMovie(
                        movie = movie,
                        condition = isFavouriteMovie.value,
                    )
                    isFavouriteMovie.value = !isFavouriteMovie.value
                }
        )
        Column(
            modifier = Modifier
                .padding(start = 6.dp)
                .align(Alignment.BottomStart)
        ) {
            Text(
                modifier = Modifier.padding(start = 2.dp),
                text = getTagLine(genres = genres, genreIds = movie.genreIds),
                style = TextStyle(
                    color = TagLineColor,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                ),
            )
            RatingBarAndCountReviews(movie=movie)
        }
    }
}

private fun getTagLine(genreIds: List<Int>, genres: List<GenresItem>): String {
    return genres.filter { it.id in genreIds }.joinToString { it.name }
}


@Composable
fun ImagePoster(imageUrlPath: String) {
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            //gradient image
            .drawWithCache {
                val gradient = Brush.verticalGradient(
                    colors = listOf(StartGradientColor, EndGradientColor),
                    startY = size.height / 3,
                    endY = size.height,
                    tileMode = TileMode.Clamp
                )
                onDrawWithContent {
                    drawContent()
                    drawRect(gradient, blendMode = BlendMode.Multiply)
                }
            },
        model = (MoviesApi.BASE_IMAGE_URL + imageUrlPath),
        contentDescription = stringResource(id = R.string.poster_movie_description),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.ic_baseline_cloud_download_24),
        fallback = painterResource(R.drawable.ic_baseline_sms_failed_24)
    )
}

@Composable
fun RatingBarAndCountReviews(movie: Movie){
    Row(modifier = Modifier.padding(top = 4.dp)) {
        val vote = (movie.voteAverage?.div(2))?.toInt()
        (0..4).map {
            if (it < (vote ?: 0)) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star_unable_icon),
                    contentDescription = "$it star rating",
                    modifier = Modifier
                        .size(12.dp)
                        .padding(start = 2.dp),
                    tint = TagLineColor,
                )
            } else {
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
