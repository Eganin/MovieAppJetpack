package com.eganin.jetpack.thebest.movieapp.ui.presentation.utils

import android.content.Context
import android.net.ConnectivityManager


fun isConnection(context: Context): Boolean{
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.activeNetworkInfo
    return netInfo != null && netInfo.isConnectedOrConnecting
}