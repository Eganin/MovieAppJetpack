package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.eganin.jetpack.thebest.movieapp.base.EventHandler
import com.eganin.jetpack.thebest.movieapp.domain.TypeObject
import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.FavouriteEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.GenresItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.Movie
import com.eganin.jetpack.thebest.movieapp.domain.data.notifications.MovieNotificationsManager
import com.eganin.jetpack.thebest.movieapp.domain.data.paging.DBPaginatorImpl
import com.eganin.jetpack.thebest.movieapp.domain.data.paging.DefaultPaginator
import com.eganin.jetpack.thebest.movieapp.domain.data.paging.SearchPaginator
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.list.MovieRepository
import com.eganin.jetpack.thebest.movieapp.domain.data.utils.toMovie
import com.eganin.jetpack.thebest.movieapp.domain.data.utils.toMovieEntity
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.list.models.ListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    notificationsManager: MovieNotificationsManager,
) : ViewModel(), EventHandler<ListViewState> {
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "CoroutineExceptionHandler got $exception")
        _listViewState.postValue(ListViewState.Error)
    } + SupervisorJob()

    //liveData - для сохранения genre list
    private val _genresData = MutableLiveData<List<GenresItem>>(emptyList())
    val genresData: LiveData<List<GenresItem>> = _genresData

    private val _listViewState: MutableLiveData<ListViewState> =
        MutableLiveData(ListViewState.Loading)
    val listViewState: LiveData<ListViewState> = _listViewState

    // для отслеживания любимых фильмов
    private val isFavouriteMovie = mutableStateOf(false)

    var mainScreenState by mutableStateOf(MainScreenState())
    var searchScreenState by mutableStateOf(SearchScreenState())
    var dbScreenState by mutableStateOf(DBScreenState())

    // paging for main list movies
    private val paginator = DefaultPaginator(
        initialKey = mainScreenState.page,
        onLoadUpdated = {
            updateLoading(isLoaded = it)
        },
        onRequest = { nextPage ->
            val result = movieRepository.downloadMovies(
                page = nextPage,
                typeMovies = TypeObject.type,
            ).results
            deleteAllDataDB()
            saveDataDB(list = result)
            result
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

    // paging for search movies
    private val paginatorSearch = SearchPaginator(
        initialKey = searchScreenState.page,
        onLoadUpdated = {
            updateLoading(isLoaded = it)
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

    // paging for db movies
    private val dbPaginator = DBPaginatorImpl(
        onLoadUpdated = {
            updateLoading(isLoaded = it)
        },
        onRequest = {
            downloadDataFromDB()
        },
        onSuccess = { items ->
            dbScreenState = dbScreenState.copy(
                items = items,
            )
        }
    )

    init {
        notificationsManager.init()
    }

    private fun updateLoading(isLoaded: Boolean) {
        if (isLoaded) {
            _listViewState.postValue(ListViewState.Loading)
        } else {
            _listViewState.postValue(ListViewState.Display)
        }
    }

    override fun obtainEvent(event: ListViewState) {
        _listViewState.value?.let {
            _listViewState.postValue(event)
            if(it is ListViewState.Loading) loadData()
        }
    }

    private fun loadData() {
        // загружаем данные из БД
        loadItemsFromDB()
        // загружаем даннеы из интернета
        /*
        loadNextItems()
        // сохраняем genres
        viewModelScope.launch(exceptionHandler) {
            _genresData.postValue(movieRepository.downloadGenres())
        }

         */
    }

    fun loadNextItems() {
        viewModelScope.launch(exceptionHandler) {
            paginator.loadNextItems()
            loadWrapper(items = mainScreenState.items)
        }
    }

    fun loadSearchItems(query: String) {
        viewModelScope.launch(exceptionHandler) {
            paginatorSearch.loadNextItems(query = query)
            loadWrapper(items = searchScreenState.items)
        }
    }

    private fun loadItemsFromDB() {
        viewModelScope.launch(exceptionHandler) {
            dbPaginator.loadItems()
            loadWrapper(items = dbScreenState.items)
        }
    }

    private fun loadWrapper(items: List<Movie>) {
        if (items.isEmpty()) {
            _listViewState.postValue(ListViewState.NoItems)
        }
    }

    private suspend fun downloadDataFromDB(): List<Movie> {
        val result = movieRepository.getAllMovies()
        if (result.isNotEmpty()) {
            // выгружаем genres
            _genresData.value = result[0].genres
        }
        return result.map { it.toMovie() }
    }

    private suspend fun saveDataDB(list: List<Movie>) {
        movieRepository.insertMovies(movies = list.map {
            it.toMovieEntity(
                genres = _genresData.value ?: emptyList()
            )
        })
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

data class DBScreenState(
    val isLoading: Boolean = false,
    val items: List<Movie> = emptyList(),
)