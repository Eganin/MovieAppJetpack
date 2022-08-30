package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.JetMovieTheme
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.MainTheme

@Composable
fun ViewError() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(JetMovieTheme.colors.primaryBackground)
    ) {
        Text(
            text = stringResource(R.string.error_label),
            color = JetMovieTheme.colors.errorColor,
            style = JetMovieTheme.typography.heading,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
@Preview
private fun ListViewNoItemsPreview() {
    MainTheme(darkTheme = true) {
        ViewError()
    }
}