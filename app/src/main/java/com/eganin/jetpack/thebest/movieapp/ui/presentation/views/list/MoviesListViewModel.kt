package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.*
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.FavouriteEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.GenresItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.Movie
import com.eganin.jetpack.thebest.movieapp.domain.data.notifications.MovieNotificationsManager
import com.eganin.jetpack.thebest.movieapp.domain.data.paging.DefaultPaginator
import com.eganin.jetpack.thebest.movieapp.domain.data.paging.SearchPaginator
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.list.MovieRepository
import com.eganin.jetpack.thebest.movieapp.domain.data.utils.toMovie
import com.eganin.jetpack.thebest.movieapp.domain.data.utils.toMovieEntity
import kotlinx.coroutines.*


class MoviesListViewModel(
    private val movieRepository: MovieRepository,
    private val notificationsManager: MovieNotificationsManager,
    private val typeMovies: TypeMovies,
) : ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "CoroutineExceptionHandler got $exception")
    } + SupervisorJob()

    //liveData - для сохранения genre list
    private val _genresData = MutableLiveData<List<GenresItem>>(emptyList())
    val genresData: LiveData<List<GenresItem>> = _genresData

    // для отслеживания любимых фильмов
    private val isFavouriteMovie = mutableStateOf(false)

    // для хранения листа фильмов
    private val _moviesData = MutableLiveData<List<Movie>>(emptyList())
    val moviesData: LiveData<List<Movie>> = _moviesData

    var mainScreenState by mutableStateOf(MainScreenState())
    var searchScreenState by mutableStateOf(SearchScreenState())

    private val paginator = DefaultPaginator(
        initialKey = mainScreenState.page,
        onLoadUpdated = {
            mainScreenState = mainScreenState.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            Log.d("EEE",typeMovies.value)
            movieRepository.downloadMovies(
                page = nextPage,
                typeMovies = typeMovies,
            ).results
        },
        getNextKey = {
            mainScreenState.page + 1
        },
        onSuccess = { items, newKey ->
            mainScreenState = mainScreenState.copy(
                items = mainScreenState.items + items,
                page = newKey,
                endReached = items.isEmpty(),
            )
        }
    )

    private val paginatorSearch = SearchPaginator(
        initialKey = searchScreenState.page,
        onLoadUpdated = {
            searchScreenState = searchScreenState.copy(isLoading = it)
        },
        onRequest = { nextPage, query ->
            movieRepository.downloadSearchMovies(page = nextPage, query = query).results
        },
        getNextKey = {
            searchScreenState.page + 1
        },
        onSuccess = { items, newKey ->
            searchScreenState = searchScreenState.copy(
                items = searchScreenState.items + items,
                page = newKey,
                endReached = items.isEmpty(),
            )
        }
    )

    init {
        notificationsManager.init()
        loadNextItems()
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun loadSearchItems(query : String){
        viewModelScope.launch {
            paginatorSearch.loadNextItems(query = query)
        }
    }

    private suspend fun downloadDataFromDB() {
        val result = movieRepository.getAllMovies()
        if (result.isNotEmpty()) {
            _moviesData.value = result.map { it.toMovie() }
            // выгружаем genres
            _genresData.value = result[0].genres
        }
    }

    private suspend fun saveDataDB() {
        movieRepository.insertMovies(movies = _moviesData.value?.map {
            it.toMovieEntity(
                genres = _genresData.value ?: emptyList()
            )
        }
            ?: emptyList())
    }

    private suspend fun deleteAllDataDB() {
        movieRepository.deleteAllMovies()
    }

    fun usingDBFavouriteMovie(movie: Movie, condition: Boolean) {
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

    fun existsMovie(id: Int): Pair<Boolean, Int> {
        // метод возврщает сущестоввание фильма в БД по id
        viewModelScope.launch(exceptionHandler) {
            isFavouriteMovie.value = movieRepository.getFavouriteMovieUsingID(id = id) != null
        }
        return isFavouriteMovie.value to id
    }

    class Factory(
        private val repository: MovieRepository,
        private val notificationsManager: MovieNotificationsManager,
        private val typeMovies: TypeMovies,
    ) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MoviesListViewModel(
                movieRepository = repository,
                notificationsManager = notificationsManager,
                typeMovies = typeMovies,
            ) as T
        }
    }

    companion object {
        private const val TAG = "MoviesListViewModel"
    }
}

data class MainScreenState(
    val isLoading: Boolean = false,
    val items: List<Movie> = emptyList(),
    val endReached: Boolean = false,
    val page: Int = 1,
)

data class SearchScreenState(
    val isLoading: Boolean = false,
    val items: List<Movie> = emptyList(),
    val endReached: Boolean = false,
    val page: Int = 1,
)