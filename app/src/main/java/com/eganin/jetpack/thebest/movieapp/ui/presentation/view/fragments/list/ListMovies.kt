package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.*

@Composable
fun ListMovies() {
    Column(
        modifier = Modifier
            .background(BackgroundColor)
            .fillMaxSize()
    ) {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.ic_combined_shape),
                tint = TagLineColor,
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .padding(start = 16.dp, top = 18.dp)
            )
            Text(
                text = "Popular", modifier = Modifier.padding(top = 19.dp, start = 12.dp),
                style = TextStyle(fontSize = 14.sp, color = White, fontWeight = FontWeight.Bold)
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(170.dp),
            modifier = Modifier.padding(top = 18.dp)
        ) {
            (0..10).map {
                item {
                    Image(
                        painter = painterResource(id = R.drawable.movie_test_image),
                        contentDescription = "",
                        modifier = Modifier
                            .width(170.dp)
                            .height(296.dp)
                            .background(BackgroundColor)
                            .padding(16.dp),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Preview
fun PreviewListMovie() {
    MovieAppTheme {
        Scaffold {
            ListMovies()
        }
    }
}