package com.eganin.jetpack.thebest.movieapp.presentation.view.fragments.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eganin.jetpack.thebest.movieapp.presentation.view.fragments.list.MoviesListViewModel
import kotlinx.coroutines.*

class MovieDetailsViewModel() : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.d(TAG, "CoroutineExceptionHandler got $exception")
        _stateData.value = MoviesListViewModel.State.Error
    }
    private val coroutineContext = exceptionHandler + SupervisorJob()

    private val _stateData =
        MutableLiveData<MoviesListViewModel.State>(MoviesListViewModel.State.Default)
    val stateData: LiveData<MoviesListViewModel.State> = _stateData


    companion object {
        private const val TAG = "MovieDetailViewModel"
    }
}