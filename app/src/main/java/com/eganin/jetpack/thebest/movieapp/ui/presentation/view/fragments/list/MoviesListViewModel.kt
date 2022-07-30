package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.domain.data.database.MovieDatabase
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.GenresItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.Movie
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.list.MovieRepositoryImpl
import kotlinx.coroutines.*


class MoviesListViewModel(
    private val movieRepository: MovieRepositoryImpl,
) : ViewModel() {


    var isQueryRequest = false
    var firstLaunch = true
    private var queryText = ""
    private var page = 1
    private var typeMovies = TypeMovies.POPULAR

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.d(TAG, "CoroutineExceptionHandler got $exception")
        errorLoading()
    }

    private val coroutineContext = exceptionHandler + SupervisorJob()

    private val _moviesData = MutableLiveData<List<Movie>>(emptyList())
    val moviesData: LiveData<List<Movie>> = _moviesData

    private val _moviesSearchData = MutableLiveData<List<Movie>>(emptyList())
    val moviesSearchData: LiveData<List<Movie>> = _moviesSearchData

    private val _stateData = MutableLiveData<State>(State.Default)
    val stateData: LiveData<State> = _stateData

    private val _changeMovies = MutableLiveData(TypeMovies.POPULAR.value)
    val changeMovies: LiveData<String> = _changeMovies

    var genresList: List<GenresItem>? = null

    fun downloadMoviesList(isAdapter: Boolean = false) {
        if (isAdapter) page++

        if (isQueryRequest) {
            downloadSearchMoviesList(query = queryText)
        } else {
            viewModelScope.launch(coroutineContext) {
                coroutineScope {
                    isQueryRequest = false
                    startLoading()
                    genresList = movieRepository.downloadGenres()
                    val data =
                        movieRepository.downloadMovies(page = page, typeMovies = typeMovies).results
                    val newList = mutableListOf<Movie>()
                    _moviesData.value?.let { newList.addAll(it) }
                    data?.let { newList.addAll(it) }
                    _moviesData.value = newList
                    stopLoading()
                }
            }
        }
    }

    fun downloadSearchMoviesList(query: String) {
        viewModelScope.launch(coroutineContext) {
            coroutineScope {
                isQueryRequest = true
                queryText = query
                startLoading()
                genresList = movieRepository.downloadGenres()
                val data =
                    movieRepository.downloadSearchMovies(page = page, query = queryText).results
                val newList = mutableListOf<Movie>()
                _moviesSearchData.value?.let { newList.addAll(it) }
                data?.let { newList.addAll(it) }
                _moviesSearchData.value = newList
                stopLoading()
            }
        }
    }

    fun clearData() {
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

    private fun startLoading() {
        _stateData.value = State.Loading
    }

    private fun stopLoading() {
        _stateData.value = State.Success
    }

    private fun errorLoading() {
        _stateData.value = State.Error
    }


    private fun changeMovies(typeMovies: TypeMovies) {
        firstLaunch = true
        page = 1
        _changeMovies.value = typeMovies.value
        this.typeMovies = typeMovies
        _moviesData.value = emptyList()
    }

    class Factory(
        private val repository: MovieRepositoryImpl
    ) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MoviesListViewModel(
                movieRepository = repository
            ) as T
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