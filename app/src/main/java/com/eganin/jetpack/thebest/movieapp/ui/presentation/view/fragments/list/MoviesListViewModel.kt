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

    var isQueryRequest = false
    var firstLaunch = true
    private var queryText = ""
    var page = 1

    @OptIn(ObsoleteCoroutinesApi::class)
    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "CoroutineExceptionHandler got $exception")
        errorLoading()
    } + SupervisorJob()


    private val _moviesData = MutableLiveData<List<Movie>>(emptyList())
    val moviesData: LiveData<List<Movie>> = _moviesData

    private val _moviesSearchData = MutableLiveData<List<Movie>>(emptyList())
    val moviesSearchData: LiveData<List<Movie>> = _moviesSearchData

    private val _stateData = MutableLiveData<State>(State.Default)
    val stateData: LiveData<State> = _stateData

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

    fun downloadMovies(isAdapter: Boolean = false) {
        viewModelScope.launch(exceptionHandler) {
            isActiveDownload = true
            startLoading()
            if (!isConnection) {
                downloadDataFromDB()
                getChoiceMovie()
            }
            if (isAdapter) page++
            if (isQueryRequest) {
                downloadSearchMoviesList(query = queryText)
            } else {
                downloadMovieList()
            }
            stopLoading()
            isActiveDownload = false

        }

    }

    fun downloadSearchMoviesList(query: String) {
        viewModelScope.launch(exceptionHandler) {
            isQueryRequest = true
            queryText = query
            _genresData.value = movieRepository.downloadGenres()
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
        _genresData.value = movieRepository.downloadGenres()
        val data =
            movieRepository.downloadMovies(
                page = page,
                typeMovies = changeMovies.value ?: TypeMovies.POPULAR
            ).results
        val newList = mutableListOf<Movie>()
        _moviesData.value?.let { newList.addAll(it) }
        data?.let { newList.addAll(it) }
        _moviesData.value = newList
        saveDataDB(movies = newList)
    }

    fun download() {
        viewModelScope.launch {
            loading.value = true
            _moviesData.value = movieRepository.downloadMovies(
                page = page,
                typeMovies = changeMovies.value ?: TypeMovies.POPULAR
            ).results?: emptyList()
            loading.value = false
        }
    }

    fun downloadSearch(query: String) {
        viewModelScope.launch {
            loading.value = true
            _moviesData.value =
                movieRepository.downloadSearchMovies(page = page, query = query).results
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
        _moviesData.value = result
    }

    private suspend fun saveDataDB(movies: List<Movie>) {
        movieRepository.insertMovies(movies = movies.map { it.toMovieEntity(genres = _genresData.value) })
    }

    private suspend fun deleteAllDataDB() {
        movieRepository.deleteAllMovies()
    }

    private fun clearData() {
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
            viewModelScope.launch(exceptionHandler) {
                deleteAllDataDB()
            }
        }
        firstLaunch = true
        page = 1
        //_changeMovies.value = typeMovies.value
        saveChoiceMovie()
        //this.typeMovies = typeMovies
        _moviesData.value = emptyList()
    }

    fun changeTypeMovies(type: TypeMovies) {
        _changeMovies.value = type
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
                clearData()
                if (!isConnection) {
                    viewModelScope.launch(exceptionHandler) {
                        deleteAllDataDB()
                    }
                }
                true
            }
            else -> false
        }

    private fun saveChoiceMovie() {
        sharedPreferences.edit {
            //putString(TOKEN_CHOICE_MOVIE, _changeMovies.value)
        }
    }

    private fun getChoiceMovie() {
        firstLaunch = false
        //_changeMovies.value =
        //sharedPreferences.getString(TOKEN_CHOICE_MOVIE, TypeMovies.POPULAR.value)
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