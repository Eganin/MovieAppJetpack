package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.utils

import android.annotation.SuppressLint
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ShowSnackBar(text: String, scaffoldState: ScaffoldState) {
    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        val result = scaffoldState.snackbarHostState.showSnackbar(
            message = text
        )
        when (result) {
            /*
            возвращаем результат в любом случае, чтобы SnackBar показался один раз
             */
            SnackbarResult.ActionPerformed -> return@launch
            SnackbarResult.Dismissed -> return@launch
        }
    }
}