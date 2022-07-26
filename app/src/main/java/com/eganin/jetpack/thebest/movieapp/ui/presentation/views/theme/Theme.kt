package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainTheme(
    style: JetMovieStyle = JetMovieStyle.Orange,
    textSize: JetMovieSize = JetMovieSize.Medium,
    paddingSize: JetMovieSize = JetMovieSize.Small,
    corners: JetMovieCorners = JetMovieCorners.Rounded,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = when (darkTheme) {
        true -> {
            when (style) {
                JetMovieStyle.Purple -> purpleDarkPalette
                JetMovieStyle.Blue -> blueDarkPalette
                JetMovieStyle.Orange -> orangeDarkPalette
                JetMovieStyle.Red -> redDarkPalette
                JetMovieStyle.Green -> greenDarkPalette
            }
        }
        false -> {
            when (style) {
                JetMovieStyle.Purple -> purpleLightPalette
                JetMovieStyle.Blue -> blueLightPalette
                JetMovieStyle.Orange -> orangeLightPalette
                JetMovieStyle.Red -> redLightPalette
                JetMovieStyle.Green -> greenLightPalette
            }
        }
    }

    val typography = JetMovieTypography(
        heading = TextStyle(
            fontSize = when (textSize) {
                JetMovieSize.Small -> 30.sp
                JetMovieSize.Medium -> 35.sp
                JetMovieSize.Big -> 40.sp
            },
            fontWeight = FontWeight.Bold
        ),
        body = TextStyle(
            fontSize = when (textSize) {
                JetMovieSize.Small -> 12.sp
                JetMovieSize.Medium -> 14.sp
                JetMovieSize.Big -> 16.sp
            },
            fontWeight = FontWeight.Normal
        ),

        caption = TextStyle(
            fontSize = when (textSize) {
                JetMovieSize.Small -> 14.sp
                JetMovieSize.Medium -> 16.sp
                JetMovieSize.Big -> 18.sp
            },
            fontWeight = FontWeight.Bold,
        )
    )

    val shapes = JetMovieShape(
        padding = when (paddingSize) {
            JetMovieSize.Small -> 0.dp
            JetMovieSize.Medium -> 3.dp
            JetMovieSize.Big -> 5.dp
        },
        cornersStyle = when (corners) {
            JetMovieCorners.Flat -> RoundedCornerShape(0.dp)
            JetMovieCorners.Rounded -> RoundedCornerShape(8.dp)
        }
    )

    CompositionLocalProvider(
        LocalJetMovieColors provides colors,
        LocalJetMovieTypography provides typography,
        LocalJetMovieShape provides shapes,
        content = content
    )
}
