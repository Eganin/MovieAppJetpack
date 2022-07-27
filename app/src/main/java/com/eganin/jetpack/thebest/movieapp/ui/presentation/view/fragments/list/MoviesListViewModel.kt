package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list

import android.util.Log
import androidx.lifecycle.*
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.GenresItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.Movie
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.MovieResponse
import com.eganin.jetpack.thebest.movieapp.domain.data.models.repositories.MovieRepository
import kotlinx.coroutines.*


class MoviesListViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private var typeMovies = TypeMovies.POPULAR

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.d(TAG, "CoroutineExceptionHandler got $exception")
        _stateData.value = State.Error
    }

    private val coroutineContext = exceptionHandler + SupervisorJob()

    private val _moviesData = MutableLiveData<List<Movie>>()
    val moviesData: LiveData<List<Movie>> = _moviesData

    private val _stateData = MutableLiveData<State>(State.Default)
    val stateData: LiveData<State> = _stateData

    private val _changeMovies = MutableLiveData(TypeMovies.POPULAR.value)
    val changeMovies: LiveData<String> = _changeMovies

    var genresList: List<GenresItem>? = null

    fun downloadMoviesList() {
        viewModelScope.launch(coroutineContext) {
            _stateData.value = State.Loading
            genresList = movieRepository.downloadGenres()
            _moviesData.value =
                movieRepository.downloadMovies(page = 1, typeMovies = typeMovies).results
            _stateData.value = State.Success
        }
    }

    fun downloadSearchMoviesList(query: String) {
        viewModelScope.launch(coroutineContext) {
            _stateData.value = State.Loading
            genresList = movieRepository.downloadGenres()
            _moviesData.value =
                movieRepository.downloadSearchMovies(page = 1, query = query).results
            _stateData.value = State.Success
        }
    }

    fun clearData(){
        _moviesData.value = emptyList()
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
            else -> false
        }


    private fun changeMovies(typeMovies: TypeMovies) {
        _changeMovies.value = typeMovies.value
        this.typeMovies = typeMovies
        _moviesData.value = emptyList()
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