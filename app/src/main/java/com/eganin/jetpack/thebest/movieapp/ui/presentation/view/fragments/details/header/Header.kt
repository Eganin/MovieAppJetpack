package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.MoviesApi
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.AdultColor
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.TopMenuColor
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.White

@Composable
fun Header(adult: Boolean, imagePath: String) {
    Box {
        AsyncImage(
            model = (MoviesApi.BASE_IMAGE_URL_BACKDROP+ imagePath),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_baseline_cloud_download_24),
            fallback = painterResource(R.drawable.ic_baseline_sms_failed_24),
            contentDescription = stringResource(
                id = R.string.content_description_background_image
            ),
            modifier = Modifier
                .height(298.dp)
                .fillMaxWidth()
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
                }
        )
        Text(
            text = stringResource(id = R.string.forward_back_text_view_path) + " " +
                    stringResource(id = R.string.forward_back_text_view_text),
            style = TextStyle(fontSize = 12.sp, color = TopMenuColor),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 18.dp, top = 53.dp)
                .clickable { },
        )

        Card(
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 26.dp)
        ) {
            Text(
                text = if(adult) "18+" else "12+",
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
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_calendar_month_24),
                contentDescription = "Calendar for scheduled viewing",
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .background(AdultColor)
                    .padding(5.dp)
                    .clickable { },
            )
        }

    }
}
