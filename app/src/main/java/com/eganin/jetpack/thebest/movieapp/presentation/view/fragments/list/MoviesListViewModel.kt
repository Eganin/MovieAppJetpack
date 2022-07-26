package com.eganin.jetpack.thebest.movieapp.presentation.view.fragments.list

import android.util.Log
import androidx.lifecycle.*
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.data.models.network.entities.GenresItem
import com.eganin.jetpack.thebest.movieapp.data.models.network.entities.MovieResponse
import com.eganin.jetpack.thebest.movieapp.data.models.repositories.MovieRepository
import kotlinx.coroutines.*


class MoviesListViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.d(TAG, "CoroutineExceptionHandler got $exception")
        _stateData.value = State.Error
    }

    private val coroutineContext = exceptionHandler + SupervisorJob()

    private val _moviesData = MutableLiveData<MovieResponse>()
    val moviesData: LiveData<MovieResponse> = _moviesData

    private val _stateData = MutableLiveData<State>(State.Default)
    val stateData: LiveData<State> = _stateData

    private val _changeMovies = MutableLiveData<String>()
    val changeMovies: LiveData<String> = _changeMovies

    var genresList: List<GenresItem>? = null

    fun downloadMoviesList(typeMovies: TypeMovies) {
        viewModelScope.launch(coroutineContext) {
            _stateData.value = State.Loading
            genresList = movieRepository.downloadGenres()
            _moviesData.value = movieRepository.downloadMovies(page = 1, typeMovies = typeMovies)
            _stateData.value = State.Success
        }
    }

    fun changeMoviesList(idPage: Int) =
        when (idPage) {
            R.id.page_1 -> {
                changeMovies(typeMovies = TypeMovies.POPULAR)
                true
            }
            R.id.page_2 -> {
                changeMovies(typeMovies = TypeMovies.TOP_RATED)
                true
            }
            R.id.page_3 -> {
                changeMovies(typeMovies = TypeMovies.NOW_PLAYING)
                true
            }
            R.id.page_4 -> {
                changeMovies(typeMovies = TypeMovies.UP_COMING)
                true
            }
            R.id.page_5 -> {
                changeMovies(typeMovies = TypeMovies.SEARCH)
                true
            }
            else -> false
        }


    private fun changeMovies(typeMovies: TypeMovies) {
        _changeMovies.value = typeMovies.value
        downloadMoviesList(typeMovies = typeMovies)
    }

    class Factory(private val repository: MovieRepository) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MoviesListViewModel(movieRepository = repository) as T
        }
    }


    sealed class State {
        object Default : State()
        object Loading : State()
        object Error : State()
        object Success : State()
    }

    companion object {
        private const val TAG = "MoviesListViewModel"
    }
}