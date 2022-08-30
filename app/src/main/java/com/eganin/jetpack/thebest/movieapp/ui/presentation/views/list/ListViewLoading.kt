package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.JetMovieTheme
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.MainTheme
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.utils.ProgressBar

@Composable
fun ListViewLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(JetMovieTheme.colors.primaryBackground)
    ) {
        ProgressBar()
    }
}

@Composable
@Preview
private fun ListViewNoItemsPreview() {
    MainTheme(darkTheme = true) {
        ListViewLoading()
    }
}