package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.JetMovieTheme
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.MainTheme

data class MenuItemModel(
    val title: String,
    val currentIndex: Int = 0,
    val values: List<String>,
)

@Composable
fun MenuItem(
    model: MenuItemModel,
    onItemSelected: ((Int) -> Unit)? = null,
) {
    val isDropdownOpen = remember { mutableStateOf(false) }
    val currentPosition = remember { mutableStateOf(model.currentIndex) }

    Box(
        modifier = Modifier
            .background(JetMovieTheme.colors.primaryBackground)
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .clickable { isDropdownOpen.value = true }
                .padding(16.dp+JetMovieTheme.shapes.padding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = model.title,
                modifier = Modifier.weight(1f),
                style = JetMovieTheme.typography.caption,
                color = JetMovieTheme.colors.primaryText,
            )

            Text(
                text = model.values[currentPosition.value],
                style = JetMovieTheme.typography.body,
                color = JetMovieTheme.colors.secondaryText,
            )

            Icon(
                modifier = Modifier
                    .size(18.dp+JetMovieTheme.shapes.padding)
                    .align(Alignment.CenterVertically),
                painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_ios_24),
                contentDescription = "Arrow choice",
                tint = JetMovieTheme.colors.secondaryText
            )
        }

        DropdownMenu(
            expanded = isDropdownOpen.value,
            onDismissRequest = { isDropdownOpen.value = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(JetMovieTheme.colors.primaryBackground)
        ) {
            model.values.forEachIndexed { index, value ->
                DropdownMenuItem(onClick = {
                    currentPosition.value = index
                    isDropdownOpen.value = false
                    onItemSelected?.invoke(index)
                }) {
                    Text(
                        text = value,
                        style = JetMovieTheme.typography.caption,
                        color = JetMovieTheme.colors.primaryText
                    )
                }
            }
        }

        Divider(
            modifier = Modifier.padding(start = 16.dp + JetMovieTheme.shapes.padding),
            thickness = 0.5.dp,
            color = JetMovieTheme.colors.secondaryText
        )
    }
}

@Composable
@Preview
fun MenuItem_Preview() {
    MainTheme(
        darkTheme = true
    ) {
        MenuItem(
            model = MenuItemModel(
                title = "Font Size",
                values = listOf(
                    stringResource(id = R.string.title_font_size_small),
                    stringResource(id = R.string.title_font_size_medium),
                    stringResource(id = R.string.title_font_size_big)
                )
            )
        )
    }
}