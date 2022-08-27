package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.*

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean,
    currentTextSize: JetMovieSize,
    currentPaddingSize: JetMovieSize,
    currentCornersStyle: JetMovieCorners,
    onDarkModeChanged: (Boolean) -> Unit,
    onNewStyle: (JetMovieStyle) -> Unit,
    onTextSizeChanged: (JetMovieSize) -> Unit,
    onPaddingSizeChanged: (JetMovieSize) -> Unit,
    onCornersStyleChanged: (JetMovieCorners) -> Unit,
) {
    Surface(
        modifier = modifier,
        color = JetMovieTheme.colors.primaryBackground,
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = stringResource(R.string.settings_label),
                modifier = Modifier
                    .padding(
                        start = 16.dp + JetMovieTheme.shapes.padding,
                        top = 16.dp + JetMovieTheme.shapes.padding
                    ),
                color = JetMovieTheme.colors.primaryText,
                style = JetMovieTheme.typography.heading,
            )
            Row(
                modifier = Modifier.padding(
                    start = 16.dp + JetMovieTheme.shapes.padding,
                    top = 16.dp + JetMovieTheme.shapes.padding
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.enable_dark_theme_label_checkbox),
                    modifier = Modifier.weight(1f),
                    color = JetMovieTheme.colors.primaryText,
                    style = JetMovieTheme.typography.caption
                )
                Checkbox(
                    checked = isDarkMode, onCheckedChange = {
                        onDarkModeChanged(it)
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = JetMovieTheme.colors.tintColor,
                        uncheckedColor = JetMovieTheme.colors.secondaryText,
                    )
                )
            }
            Divider(
                modifier = Modifier.padding(start = 16.dp + JetMovieTheme.shapes.padding),
                thickness = 0.5.dp,
                color = JetMovieTheme.colors.secondaryText
            )

            MenuItem(model = MenuItemModel(
                title = stringResource(R.string.font_size_settings_label),
                currentIndex = when (currentTextSize) {
                    JetMovieSize.Small -> 0
                    JetMovieSize.Medium -> 1
                    JetMovieSize.Big -> 2
                },
                values = listOf(
                    stringResource(id = R.string.title_font_size_small),
                    stringResource(id = R.string.title_font_size_medium),
                    stringResource(id = R.string.title_font_size_big)
                )
            ),
                onItemSelected = {
                    when (it) {
                        0 -> onTextSizeChanged(JetMovieSize.Small)
                        1 -> onTextSizeChanged(JetMovieSize.Medium)
                        2 -> onTextSizeChanged(JetMovieSize.Big)
                    }
                }
            )

            MenuItem(model = MenuItemModel(
                title = stringResource(R.string.margins_settings_label),
                currentIndex = when (currentPaddingSize) {
                    JetMovieSize.Small -> 0
                    JetMovieSize.Medium -> 1
                    JetMovieSize.Big -> 2
                },
                values = listOf(
                    stringResource(id = R.string.title_font_size_small),
                    stringResource(id = R.string.title_font_size_medium),
                    stringResource(id = R.string.title_font_size_big)
                )
            ),
                onItemSelected = {
                    when (it) {
                        0 -> onPaddingSizeChanged(JetMovieSize.Small)
                        1 -> onPaddingSizeChanged(JetMovieSize.Medium)
                        2 -> onPaddingSizeChanged(JetMovieSize.Big)
                    }
                }
            )
            MenuItem(model = MenuItemModel(
                title = stringResource(R.string.card_style_settings_label),
                currentIndex = when (currentCornersStyle) {
                    JetMovieCorners.Rounded -> 0
                    JetMovieCorners.Flat -> 1
                },
                values = listOf(
                    stringResource(R.string.rounded_settings_label),
                    stringResource(R.string.flat_settings_label),
                )
            ),
                onItemSelected = {
                    when (it) {
                        0 -> onCornersStyleChanged(JetMovieCorners.Rounded)
                        1 -> onCornersStyleChanged(JetMovieCorners.Flat)
                    }
                }
            )

            Row(
                modifier = Modifier
                    .padding(JetMovieTheme.shapes.padding + 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ColorCard(color = if (isDarkMode) purpleDarkPalette.tintColor else purpleLightPalette.tintColor,
                    onClick = {
                        onNewStyle(JetMovieStyle.Purple)
                    })
                ColorCard(color = if (isDarkMode) orangeDarkPalette.tintColor else orangeLightPalette.tintColor,
                    onClick = {
                        onNewStyle(JetMovieStyle.Orange)
                    })
                ColorCard(color = if (isDarkMode) blueDarkPalette.tintColor else blueLightPalette.tintColor,
                    onClick = {
                        onNewStyle(JetMovieStyle.Blue)
                    })
            }

            Row(
                modifier = Modifier
                    .padding(JetMovieTheme.shapes.padding + 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ColorCard(color = if (isDarkMode) redDarkPalette.tintColor else redLightPalette.tintColor,
                    onClick = {
                        onNewStyle(JetMovieStyle.Red)
                    })
                ColorCard(color = if (isDarkMode) greenDarkPalette.tintColor else greenLightPalette.tintColor,
                    onClick = {
                        onNewStyle(JetMovieStyle.Green)
                    })
            }
        }
    }
}

@Composable
fun ColorCard(color: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(60.dp)
            .clickable { onClick() },
        backgroundColor = color,
        elevation = 8.dp,
        shape = JetMovieTheme.shapes.cornersStyle
    ) {}
}