package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.FavouriteEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.GenresItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.Movie
import com.eganin.jetpack.thebest.movieapp.domain.data.notifications.MovieNotificationsManager
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.list.MovieRepository
import com.eganin.jetpack.thebest.movieapp.ui.presentation.utils.toMovie
import com.eganin.jetpack.thebest.movieapp.ui.presentation.utils.toMovieEntity
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext


class MoviesListViewModel(
    private val movieRepository: MovieRepository,
    val notificationsManager: MovieNotificationsManager,
) : ViewModel() {

    private var page = 1

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "CoroutineExceptionHandler got $exception")
    } + SupervisorJob()
    private val _changeMovies = MutableLiveData(TypeMovies.POPULAR)
    private val changeMovies: LiveData<TypeMovies> = _changeMovies

    private val _genresData = MutableLiveData<List<GenresItem>>(emptyList())
    val genresData: LiveData<List<GenresItem>> = _genresData

    val loading = mutableStateOf(false)
    private val isFavouriteMovie = mutableStateOf(false)

    private val _moviesData = MutableLiveData<List<Movie>>(emptyList())
    val moviesData: LiveData<List<Movie>> = _moviesData

    init {
        notificationsManager.init()
    }

    fun changeTypeMovies(type: TypeMovies) {
        _changeMovies.value = type
    }

    fun downloadMovies() {
        download {
            movieRepository.downloadMovies(
                page = page,
                typeMovies = changeMovies.value ?: TypeMovies.POPULAR
            ).results ?: emptyList()
        }
    }

    fun downloadSearch(query: String) {
        download {
            movieRepository.downloadSearchMovies(page = page, query = query).results
                ?: emptyList()
        }
    }

    private fun download(action: suspend () -> List<Movie>) {
        viewModelScope.launch {
            loading.value = true
            downloadDataFromDB()
            withContext(Dispatchers.IO) {
                _moviesData.postValue(action())
                _genresData.postValue(movieRepository.downloadGenres())
                withContext(Dispatchers.Main) {
                    deleteAllDataDB()
                    saveDataDB()
                }
            }
            loading.value = false
        }
    }

    private suspend fun downloadDataFromDB() {
        val result = movieRepository.getAllMovies()
        if(result.isNotEmpty()){
            _moviesData.value = result.map { it.toMovie() }
            _genresData.value = result[0].genres ?: emptyList()
        }
    }

    private suspend fun saveDataDB() {
        movieRepository.insertMovies(movies = _moviesData.value?.map { it.toMovieEntity(genres = _genresData.value) }
            ?: emptyList())
    }

    private suspend fun deleteAllDataDB() {
        movieRepository.deleteAllMovies()
    }

    fun usingDBFavouriteMovie(movie: Movie,condition : Boolean) {
        viewModelScope.launch(exceptionHandler) {
            if (!condition) {
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

    fun existsMovie(id: Int) : Pair<Boolean,Int>{
        viewModelScope.launch(exceptionHandler) {
            isFavouriteMovie.value = movieRepository.getFavouriteMovieUsingID(id = id) != null
        }
        return isFavouriteMovie.value to id
    }

    class Factory(
        private val repository: MovieRepository,
        private val notificationsManager: MovieNotificationsManager,
    ) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MoviesListViewModel(
                movieRepository = repository,
                notificationsManager = notificationsManager
            ) as T
        }
    }

    companion object {
        private const val TAG = "MoviesListViewModel"
    }
}