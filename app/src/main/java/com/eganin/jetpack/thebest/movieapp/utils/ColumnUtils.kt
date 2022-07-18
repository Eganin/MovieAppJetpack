package com.eganin.jetpack.thebest.movieapp.utils

import android.util.DisplayMetrics
import android.view.Display

fun getColumnCountUtils(display : Display) : Int{
    val displayMetrics = DisplayMetrics()

    display.getMetrics(displayMetrics)
    val width = (displayMetrics.widthPixels / displayMetrics.density).toInt()
    return if (width / 185 > 2) width / 185 else 2
}