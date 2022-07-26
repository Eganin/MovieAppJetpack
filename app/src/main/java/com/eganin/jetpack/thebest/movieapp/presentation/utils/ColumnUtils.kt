package com.eganin.jetpack.thebest.movieapp.presentation.utils

import android.util.DisplayMetrics
import android.view.Display

fun getColumnCountUtils(display : Display?) : Int{
    val DEFAULT_COLUMN_COUNT = 2
    val displayMetrics = DisplayMetrics()

    display?.let {
        display.getMetrics(displayMetrics)
        val width = (displayMetrics.widthPixels / displayMetrics.density).toInt()
        return if (width / 185 > 2) width / 185 else DEFAULT_COLUMN_COUNT
    }
    return DEFAULT_COLUMN_COUNT
}