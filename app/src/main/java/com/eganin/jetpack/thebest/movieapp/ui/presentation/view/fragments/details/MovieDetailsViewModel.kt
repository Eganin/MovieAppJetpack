package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details

import android.util.Log
import androidx.lifecycle.*
import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.MovieDetailsEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.CastItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.MovieDetailsResponse
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.details.MovieDetailsRepositoryImpl
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.MoviesListViewModel
import kotlinx.coroutines.*

class MovieDetailsViewModel(
    private val repository: MovieDetailsRepositoryImpl,
    isConnection: Boolean
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.d(TAG, "CoroutineExceptionHandler got $exception")
        _stateData.value = MoviesListViewModel.State.Error
    }
    private val coroutineContext = exceptionHandler + SupervisorJob()

    private val _stateData =
        MutableLiveData<MoviesListViewModel.State>(MoviesListViewModel.State.Default)
    val stateData: LiveData<MoviesListViewModel.State> = _stateData

    private val _detailsData = MutableLiveData<MovieDetailsResponse>()
    val detailsData: LiveData<MovieDetailsResponse> = _detailsData

    private val _castData = MutableLiveData<List<CastItem>>()
    val castData: LiveData<List<CastItem>> = _castData

    fun downloadDetailsData(id: Int) {
        viewModelScope.launch(coroutineContext) {
            _stateData.value = MoviesListViewModel.State.Loading
            _detailsData.value = repository.downloadDetailsInfoForMovie(movieId = id)
            _castData.value = repository.downloadCredits(movieId = id).cast
            _stateData.value = MoviesListViewModel.State.Success
        }
    }

    private suspend fun downloadDataFromDB(id: Int) {
        repository.getAllInfoMovie(id = id)
    }

    private suspend fun saveDataDB(response: MovieDetailsResponse) {
        repository.insertMovieDetails(movie = response.toMovieDetailsEntity())
    }

    private fun MovieDetailsResponse.toMovieDetailsEntity(): MovieDetailsEntity {
        return MovieDetailsEntity(
            id = this.id,
            title = this.title,
            backdropPath = this.backdropPath,
            genres = this.genres,
            popularity = this.popularity,
            voteCount = this.voteCount,
            overview = this.overview,
            originalTitle = this.originalTitle,
            runtime = this.runtime,
            posterPath = this.posterPath,
            voteAverage = this.voteAverage,
            adult = this.adult,
        )
    }


    class Factory(
        private val repository: MovieDetailsRepositoryImpl,
        private val isConnection: Boolean
    ) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MovieDetailsViewModel(repository = repository, isConnection = isConnection) as T
        }
    }

    companion object {
        private const val TAG = "MovieDetailViewModel"
    }
}