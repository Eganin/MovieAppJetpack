package com.eganin.jetpack.thebest.movieapp.presentation.view.fragments.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eganin.jetpack.thebest.movieapp.data.models.entities.Movie
import kotlinx.coroutines.*


class MoviesListViewModel(private val interactor: MovieInteractor) : ViewModel() {

    private val _moviesData = MutableLiveData<List<Movie>>()
    val moviesData: LiveData<List<Movie>> = _moviesData

    private val _stateData = MutableLiveData<State>(State.Default)
    val stateData: LiveData<State> = _stateData

    fun downloadMoviesList() {
        try {
            viewModelScope.launch {
                _stateData.value = State.Loading
                _moviesData.value = interactor.downloadMoviesList()
                _stateData.value = State.Success
            }
        } catch (e: Exception) {
            _stateData.value = State.Error
        }
    }

    sealed class State {
        object Default : State()
        object Loading : State()
        object Error : State()
        object Success : State()
    }
}