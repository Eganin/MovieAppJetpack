package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.details.header

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.domain.data.network.MoviesApi
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.MovieDetailsResponse
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.details.header.calendar.CalendarView
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.ui.theme.*

@Composable
fun Header(
    adult: Boolean,
    imagePath: String,
    movieInfo: MovieDetailsResponse,
    scaffoldState: ScaffoldState,
    navController: NavController,
) {
    Box {
        AsyncImage(
            model = (MoviesApi.BASE_IMAGE_URL_BACKDROP + imagePath),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_baseline_cloud_download_24),
            fallback = painterResource(R.drawable.ic_baseline_sms_failed_24),
            contentDescription = stringResource(
                id = R.string.content_description_background_image
            ),
            modifier = Modifier
                .height(298.dp)
                .fillMaxWidth()
                // gradient image
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
                }
        )
        Text(
            text = stringResource(id = R.string.forward_back_text_view_path) + " " +
                    stringResource(id = R.string.forward_back_text_view_text),
            style = TextStyle(fontSize = 12.sp, color = TopMenuColor),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 18.dp, top = 53.dp)
                .clickable {
                    navController.popBackStack()
                },
        )

        Card(
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 26.dp)
        ) {
            Text(
                text = if (adult) "18+" else "12+",
                modifier = Modifier
                    .background(AdultColor)
                    .padding(5.dp),
                style = TextStyle(color = White, fontSize = 12.sp),
            )
        }

        Card(
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 65.dp, bottom = 23.dp)
        ) {
            CalendarView(
                movieInfo = movieInfo,
                scaffoldState = scaffoldState
            )
        }
    }
}
