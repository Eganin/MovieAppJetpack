package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details

import android.util.Log
import androidx.lifecycle.*
import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.MovieDetailsEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.CastItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.MovieDetailsResponse
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.details.MovieDetailsRepositoryImpl
import com.eganin.jetpack.thebest.movieapp.ui.presentation.utils.isConnection
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.MoviesListViewModel
import kotlinx.coroutines.*

class MovieDetailsViewModel(
    private val repository: MovieDetailsRepositoryImpl,
    private val isConnection: Boolean
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
            if (!isConnection) downloadDataFromDB(id = id)
            download(id = id)
            _stateData.value = MoviesListViewModel.State.Success
        }
    }

    private suspend fun download(id: Int) {
        val resultMovieDetails = repository.downloadDetailsInfoForMovie(movieId = id)
        val resultCasts = repository.downloadCredits(movieId = id).cast
        resultCasts?.forEach {
            it.movieId = id
        }
        _detailsData.value = resultMovieDetails
        _castData.value = resultCasts
        resultCasts?.let { saveDataDB(response = resultMovieDetails, credits = it) }
    }

    private suspend fun downloadDataFromDB(id: Int) {
        _detailsData.value = repository.getAllInfoMovie(id = id).toMovieDetailsResponse()
        _castData.value = repository.getAllCredits(id = id)
    }

    private suspend fun saveDataDB(response: MovieDetailsResponse, credits: List<CastItem>) {
        repository.insertMovieDetails(movie = response.toMovieDetailsEntity())
        repository.insertCredits(credits = credits)
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

    private fun MovieDetailsEntity.toMovieDetailsResponse(): MovieDetailsResponse {
        return MovieDetailsResponse(
            title,
            backdropPath,
            genres,
            popularity,
            id,
            voteCount,
            overview,
            originalTitle,
            runtime,
            posterPath,
            voteAverage,
            adult,
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