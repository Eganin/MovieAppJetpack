package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.settings

import android.widget.CheckBox
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.JetMovieCorners
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.JetMovieSize
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.JetMovieStyle
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.JetMovieTheme

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
                    .weight(1f)
                    .padding(start = 16.dp),
                color = JetMovieTheme.colors.primaryText,
                style = JetMovieTheme.typography.heading,
            )
            Row(modifier = Modifier.padding(16.dp)) {
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
                modifier = Modifier.padding(start = 16.dp),
                thickness = 0.5.dp,
                color = JetMovieTheme.colors.secondaryText.copy(
                    alpha = 0.3f
                )
            )

            //MenuItem()
        }
    }
}