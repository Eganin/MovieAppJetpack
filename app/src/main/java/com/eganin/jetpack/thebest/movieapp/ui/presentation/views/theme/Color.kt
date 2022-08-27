package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme

import androidx.compose.ui.graphics.Color

val BackgroundColor = Color(0xFF191926)
val StartGradientColor =Color(0x00191926)
val EndGradientColor = Color(0xFF7F7F9E)
val EditTextSearchBackGround =Color(0xFFF3F4F5)

val baseLightPalette = JetMovieColors(
    primaryBackground = Color(0xFFFFFFFF),
    primaryText = Color(0xFF3D454C),
    secondaryBackground = Color(0xFFF3F4F5),
    secondaryText = Color(0xFF6D6D80),
    tintColor = Color.Magenta,
    controlColor = Color(0xFF7A8A99),
    errorColor = Color(0xFFFF3377),
    cardBackground = Color(0xFFC5D2DF)
)

val baseDarkPalette = JetMovieColors(
    primaryBackground = BackgroundColor,
    primaryText = Color(0xFFF2F4F5),
    secondaryBackground = Color(0xFF191926),
    secondaryText = Color(0xFF6D6D80),
    tintColor = Color.Magenta,
    controlColor = Color(0xFF7A8A99),
    errorColor = Color(0xFFFF6699),
    cardBackground = Color(0xFF242438)
)

val purpleLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFFAD57D9)
)

val purpleDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFFD580FF)
)

val orangeLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFFFF6619)
)

val orangeDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFFFF974D)
)

val blueLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFF4D88FF)
)

val blueDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFF99BBFF)
)

val redLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFFE63956)
)

val redDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFFFF5975)
)

val greenLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFF12B37D)
)

val greenDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFF7EE6C3)
)