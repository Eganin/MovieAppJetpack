package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class JetMovieColors(
    val primaryText: Color,
    val primaryBackground: Color,
    val secondaryText: Color,
    val secondaryBackground: Color,
    val tintColor: Color,
    val controlColor: Color,
    val errorColor: Color,
    val cardBackground : Color,
)

data class JetMovieTypography(
    val heading: TextStyle,
    val body: TextStyle,
    val caption: TextStyle,
)

data class JetMovieShape(
    val padding: Dp,
    val cornersStyle: Shape,
)

object JetMovieTheme {
    val colors: JetMovieColors
        @Composable
        get() = LocalJetMovieColors.current

    val typography: JetMovieTypography
        @Composable
        get() = LocalJetMovieTypography.current

    val shapes: JetMovieShape
        @Composable
        get() = LocalJetMovieShape.current
}

enum class JetMovieStyle {
    Purple, Orange, Blue, Red, Green,
}

enum class JetMovieSize {
    Small, Medium, Big,
}

enum class JetMovieCorners {
    Flat, Rounded,
}

val LocalJetMovieColors = staticCompositionLocalOf<JetMovieColors> {
    error("No colors providers")
}

val LocalJetMovieTypography = staticCompositionLocalOf<JetMovieTypography> {
    error("No font provided")
}

val LocalJetMovieShape = staticCompositionLocalOf<JetMovieShape> {
    error("No shapes provided")
}

