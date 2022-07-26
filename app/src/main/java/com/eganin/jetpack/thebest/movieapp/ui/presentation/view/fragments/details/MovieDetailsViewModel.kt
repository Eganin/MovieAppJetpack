package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details

import android.util.Log
import androidx.lifecycle.*
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.CastItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.MovieDetailsResponse
import com.eganin.jetpack.thebest.movieapp.domain.data.models.repositories.MovieDetailsRepository
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.MoviesListViewModel
import kotlinx.coroutines.*

class MovieDetailsViewModel(val repository: MovieDetailsRepository) : ViewModel() {

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
    val castData : LiveData<List<CastItem>> = _castData

    fun downloadDetailsData(id: Int) {
        viewModelScope.launch(coroutineContext) {
            _stateData.value = MoviesListViewModel.State.Loading
            _detailsData.value = repository.downloadDetailsInfoForMovie(movieId = id)
            _castData.value = repository.downloadCredits(movieId = id).cast
            _stateData.value = MoviesListViewModel.State.Success
        }
    }


    class Factory(private val repository: MovieDetailsRepository) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MovieDetailsViewModel(repository = repository) as T
        }
    }

    companion object {
        private const val TAG = "MovieDetailViewModel"
    }
}