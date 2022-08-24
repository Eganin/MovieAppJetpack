package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import androidx.lifecycle.*
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.di.AppComponent.Companion.TOKEN_CHOICE_MOVIE
import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.FavouriteEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.GenresItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.Movie
import com.eganin.jetpack.thebest.movieapp.domain.data.notifications.MovieNotificationsManager
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.list.MovieRepository
import com.eganin.jetpack.thebest.movieapp.ui.presentation.utils.toMovie
import com.eganin.jetpack.thebest.movieapp.ui.presentation.utils.toMovieEntity
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel


class MoviesListViewModel(
    private val movieRepository: MovieRepository,
    private val isConnection: Boolean,
    private val sharedPreferences: SharedPreferences,
    val notificationsManager: MovieNotificationsManager,
) : ViewModel() {

    var page = 1

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "CoroutineExceptionHandler got $exception")
    } + SupervisorJob()

    val movies = mutableStateOf(emptyList<Movie>())

    private val _changeMovies = MutableLiveData(TypeMovies.POPULAR)
    val changeMovies: LiveData<TypeMovies> = _changeMovies

    private val _cacheMoviesData = MutableLiveData<List<Movie>>(emptyList())
    val cacheMoviesData: LiveData<List<Movie>> = _cacheMoviesData

    private val _genresData = MutableLiveData<List<GenresItem>>(emptyList())
    val genresData: LiveData<List<GenresItem>> = _genresData

    val loading = mutableStateOf(false)

    var isActiveDownload = false

    init {
        notificationsManager.init()
    }
    fun changeTypeMovies(type: TypeMovies) {
        _changeMovies.value = type
    }
    fun download() {
        Log.d("EEE","LOADDDDDDD")
        viewModelScope.launch {
            loading.value = true
            movies.value = movieRepository.downloadMovies(
                page = page,
                typeMovies = changeMovies.value ?: TypeMovies.POPULAR
            ).results?: emptyList()
            loading.value = false
        }
    }

    fun downloadSearch(query: String) {
        viewModelScope.launch {
            loading.value = true
            movies.value = movieRepository.downloadSearchMovies(page = page, query = query).results
                ?: emptyList()
            loading.value=false
        }
    }

    private suspend fun downloadDataFromDB() {
        val result = movieRepository.getAllMovies()
        _cacheMoviesData.value = result.map { it.toMovie() }
        result[0].genres?.let {
            _genresData.value = it
        }
    }

    private suspend fun downloadUpdateDataFromDB() {
        val result = movieRepository.getAllMovies().map { it.toMovie() }
        movies.value = result
    }

    private suspend fun saveDataDB(movies: List<Movie>) {
        movieRepository.insertMovies(movies = movies.map { it.toMovieEntity(genres = _genresData.value) })
    }

    private suspend fun deleteAllDataDB() {
        movieRepository.deleteAllMovies()
    }

    fun usingDBFavouriteMovie(movie: Movie, condition: Boolean) {
        viewModelScope.launch(exceptionHandler) {
            if (condition) {
                movieRepository.insertFavouriteMovie(
                    favouriteMovie = FavouriteEntity(
                        idMovie = movie.id,
                        title = movie.title ?: "",
                    )
                )
            } else {
                movieRepository.deleteFavouriteMovieUsingID(id = movie.id)
            }
        }
    }

    suspend fun existsMovie(id: Int): Boolean = withContext(Dispatchers.IO) {
        movieRepository.getFavouriteMovieUsingID(id = id) != null
    }

    class Factory(
        private val repository: MovieRepository,
        private val isConnection: Boolean,
        private val sharedPreferences: SharedPreferences,
        private val notificationsManager: MovieNotificationsManager,
    ) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MoviesListViewModel(
                movieRepository = repository,
                isConnection = isConnection,
                sharedPreferences = sharedPreferences,
                notificationsManager = notificationsManager
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