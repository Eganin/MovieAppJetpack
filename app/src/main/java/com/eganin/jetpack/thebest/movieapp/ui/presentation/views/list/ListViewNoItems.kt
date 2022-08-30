package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.JetMovieTheme
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.MainTheme

@Composable
fun ListViewNoItems() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(JetMovieTheme.colors.primaryBackground)
    ) {

        Column(modifier = Modifier.align(Alignment.Center)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_do_not_disturb_24),
                contentDescription = stringResource(R.string.no_items_label),
                tint = JetMovieTheme.colors.errorColor,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(100.dp),
            )
            Text(
                text = stringResource(R.string.no_items_label),
                color = JetMovieTheme.colors.errorColor,
                style = JetMovieTheme.typography.heading,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
@Preview
private fun ListViewNoItemsPreview() {
    MainTheme(darkTheme = true) {
        ListViewNoItems()
    }
}
