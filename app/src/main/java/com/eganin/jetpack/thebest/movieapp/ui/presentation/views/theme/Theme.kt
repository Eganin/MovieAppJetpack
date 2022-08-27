package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat

private val DarkColorScheme = darkColorScheme(
    primary = OnPrimaryColor,
    secondary = AdultColor,
    onPrimary = OnPrimaryColor,
    onSecondary = Black,
    background = BackgroundColor
)

private val LightColorScheme = lightColorScheme(
    primary = OnPrimaryColor,
    secondary = AdultColor,
    onPrimary = OnPrimaryColor,
    onSecondary = Black,
    background = BackgroundColor
)

@Composable
fun MainTheme(
    style: JetMovieStyle=  JetMovieStyle.Orange,
    textSize : JetMovieSize = JetMovieSize.Medium,
    paddingSize : JetMovieSize = JetMovieSize.Medium,
    corners : JetMovieCorners = JetMovieCorners.Rounded,
    darkTheme : Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
){
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
                JetMovieSize.Small -> 24.sp
                JetMovieSize.Medium -> 28.sp
                JetMovieSize.Big -> 32.sp
            },
            fontWeight = FontWeight.Bold
        ),
        body = TextStyle(
            fontSize = when (textSize) {
                JetMovieSize.Small -> 14.sp
                JetMovieSize.Medium -> 16.sp
                JetMovieSize.Big -> 18.sp
            },
            fontWeight = FontWeight.Normal
        ),
        toolbar = TextStyle(
            fontSize = when (textSize) {
                JetMovieSize.Small -> 14.sp
                JetMovieSize.Medium -> 16.sp
                JetMovieSize.Big -> 18.sp
            },
            fontWeight = FontWeight.Medium
        ),
        caption = TextStyle(
            fontSize = when (textSize) {
                JetMovieSize.Small -> 10.sp
                JetMovieSize.Medium -> 12.sp
                JetMovieSize.Big -> 14.sp
            }
        )
    )

    val shapes = JetMovieShape(
        padding = when (paddingSize) {
            JetMovieSize.Small -> 5.dp
            JetMovieSize.Medium -> 8.dp
            JetMovieSize.Big -> 12.dp
        },
        cornersStyle = when (corners) {
            JetMovieCorners.Flat -> RoundedCornerShape(0.dp)
            JetMovieCorners.Rounded -> RoundedCornerShape(8.dp)
        }
    )

    CompositionLocalProvider(
        LocalJetMovieColors provides colors,
        LocalJetMovieTypography provides typography,
        LocalJetMovieShape provides  shapes,
        content = content
    )
}
