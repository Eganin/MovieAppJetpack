package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list

import android.util.Log
import androidx.lifecycle.*
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.MovieEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.GenresItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.Movie
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.list.MovieRepositoryImpl
import com.eganin.jetpack.thebest.movieapp.ui.presentation.utils.isConnection
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


class MoviesListViewModel(
    private val movieRepository: MovieRepositoryImpl,
    private val isConnection: Boolean,
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

    private val _cacheMoviesData = MutableLiveData<List<Movie>>(emptyList())
    val cacheMoviesData: LiveData<List<Movie>> = _cacheMoviesData

    var genresList: List<GenresItem>? = null

    fun downloadMovies(isAdapter: Boolean = false) {
        viewModelScope.launch(coroutineContext) {
            coroutineScope {
                startLoading()
                if (!isConnection) downloadDataFromDB()
                if (isAdapter) page++

                if (isQueryRequest) {
                    downloadSearchMoviesList(query = queryText)
                } else {
                    downloadMovieList()
                }
                stopLoading()
            }
        }
    }

    fun downloadSearchMoviesList(query: String) {
        viewModelScope.launch(coroutineContext) {
            isQueryRequest = true
            queryText = query
            genresList = movieRepository.downloadGenres()
            val data =
                movieRepository.downloadSearchMovies(page = page, query = queryText).results
            val newList = mutableListOf<Movie>()
            _moviesSearchData.value?.let { newList.addAll(it) }
            data?.let { newList.addAll(it) }
            _moviesSearchData.value = newList
            saveDataDB(movies = newList)
        }
    }

    private suspend fun downloadMovieList() {
        isQueryRequest = false
        genresList = movieRepository.downloadGenres()
        val data =
            movieRepository.downloadMovies(page = page, typeMovies = typeMovies).results
        val newList = mutableListOf<Movie>()
        _moviesData.value?.let { newList.addAll(it) }
        data?.let { newList.addAll(it) }
        _moviesData.value = newList
        saveDataDB(movies = newList)
    }

    private suspend fun downloadDataFromDB() {
        val result = movieRepository.getAllMovies()
        _cacheMoviesData.value = result.map { it.toMovie() }
        genresList = result[0].genres
        Log.d("EEE", cacheMoviesData.value.toString())
    }

    private suspend fun saveDataDB(movies: List<Movie>) {
        movieRepository.insertMovies(movies = movies.map { it.toMovieEntity(genres = genresList) })
    }

    private suspend fun deleteAllDataDB() {
        movieRepository.deleteAllMovies()
    }

    fun clearData() {
        _moviesData.value = emptyList()
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
        if (isConnection) {
            viewModelScope.launch(coroutineContext) {
                deleteAllDataDB()
            }
        }
        firstLaunch = true
        page = 1
        _changeMovies.value = typeMovies.value
        this.typeMovies = typeMovies
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
            R.id.page_5 -> {
                viewModelScope.launch(coroutineContext) {
                    deleteAllDataDB()
                }
                true
            }
            else -> false
        }

    private fun Movie.toMovieEntity(genres: List<GenresItem>?): MovieEntity {
        return MovieEntity(
            id = this.id,
            originalTitle = this.originalTitle,
            title = this.title,
            genreIds = this.genreIds,
            posterPath = this.posterPath,
            backdropPath = this.backdropPath,
            voteAverage = this.voteAverage,
            adult = this.adult,
            voteCount = this.voteCount,
            genres = genres,
        )
    }

    private fun MovieEntity.toMovie(): Movie {
        return Movie(
            id = this.id,
            originalTitle = this.originalTitle,
            title = this.title,
            genreIds = this.genreIds,
            posterPath = this.posterPath,
            backdropPath = this.backdropPath,
            voteCount = this.voteCount,
            voteAverage = this.voteAverage,
            adult = this.adult,
        )
    }

    class Factory(
        private val repository: MovieRepositoryImpl,
        private val isConnection: Boolean,
    ) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MoviesListViewModel(
                movieRepository = repository,
                isConnection = isConnection,
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