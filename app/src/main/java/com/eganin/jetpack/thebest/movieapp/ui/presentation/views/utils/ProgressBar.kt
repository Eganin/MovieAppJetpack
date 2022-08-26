package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.ui.theme.White

@Composable
fun ProgressBar(){
    Box(modifier = Modifier.fillMaxSize()){
        CircularProgressIndicator(
            color = White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}