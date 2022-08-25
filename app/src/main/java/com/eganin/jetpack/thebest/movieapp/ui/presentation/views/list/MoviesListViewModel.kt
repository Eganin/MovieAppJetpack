package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.FavouriteEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.GenresItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.Movie
import com.eganin.jetpack.thebest.movieapp.domain.data.notifications.MovieNotificationsManager
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.list.MovieRepository
import com.eganin.jetpack.thebest.movieapp.domain.data.utils.toMovie
import com.eganin.jetpack.thebest.movieapp.domain.data.utils.toMovieEntity
import kotlinx.coroutines.*


class MoviesListViewModel(
    private val movieRepository: MovieRepository,
    val notificationsManager: MovieNotificationsManager,
) : ViewModel() {

    private var page = 1

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "CoroutineExceptionHandler got $exception")
    } + SupervisorJob()
    //livedata - для изменения типа скачиваемых фильмов
    private val _changeMovies = MutableLiveData(TypeMovies.POPULAR)
    private val changeMovies: LiveData<TypeMovies> = _changeMovies

    //liveData - для сохранения genre list
    private val _genresData = MutableLiveData<List<GenresItem>>(emptyList())
    val genresData: LiveData<List<GenresItem>> = _genresData

    //для отслеживания загрузки и отображения ProgressBar на странице
    val loading = mutableStateOf(false)

    // для отслеживания любимых фильмов
    private val isFavouriteMovie = mutableStateOf(false)

    // для хранения листа фильмов
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
            ).results
        }
    }

    fun downloadSearch(query: String) {
        download {
            movieRepository.downloadSearchMovies(page = page, query = query).results
        }
    }

    private fun download(action: suspend () -> List<Movie>) {
        viewModelScope.launch {
            loading.value = true
            // сохраняем данные в БД
            downloadDataFromDB()
            withContext(Dispatchers.IO) {
                // загружаем фильмы любого типа
                _moviesData.postValue(action())
                // сохраняем genres
                _genresData.postValue(movieRepository.downloadGenres())
                withContext(Dispatchers.Main) {
                    // очищишаем данные, чтобы точно сохранить фильмы послденго типа
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
            // выгружаем genres
            _genresData.value = result[0].genres
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
                // если фильм еще не добавлен в избрангное
                movieRepository.insertFavouriteMovie(
                    favouriteMovie = FavouriteEntity(
                        idMovie = movie.id,
                        title = movie.title,
                    )
                )
            } else {
                // если фильм уже был добавлен в избранное
                movieRepository.deleteFavouriteMovieUsingID(id = movie.id)
            }
        }
    }

    fun existsMovie(id: Int) : Pair<Boolean,Int>{
        // метод возврщает сущестоввание фильма в БД по id
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