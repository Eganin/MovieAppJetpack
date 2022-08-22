package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.Greeting
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.*

@Composable
fun MovieDetails() {
    Column(
        modifier = Modifier
            .background(BackgroundColor)
            .fillMaxSize()
    ) {
        Header()
    }
}

@Composable
fun Header() {
    Box {
        Image(
            painter = painterResource(R.drawable.img),
            contentDescription = stringResource(
                id = R.string.content_description_background_image
            ),
            modifier = Modifier
                .height(298.dp)
                .fillMaxWidth()
                .drawWithCache {
                    val gradient = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xFF191926)),
                        startY = (size.height / 2.5).toFloat(),
                        endY = size.height
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
                text = "13+",
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

@Composable
@Preview(showBackground = true)
fun MovieDetailsPreview() {
    MovieAppTheme {
        MovieDetails()
    }
}