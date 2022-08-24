package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details

import android.content.Intent
import android.os.Looper
import android.provider.CalendarContract
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.CastItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.MovieDetailsResponse
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.details.MovieDetailsRepository
import com.eganin.jetpack.thebest.movieapp.ui.presentation.utils.toMovieDetailsEntity
import com.eganin.jetpack.thebest.movieapp.ui.presentation.utils.toMovieDetailsResponse
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.MoviesListViewModel
import kotlinx.coroutines.*
import java.util.*

class MovieDetailsViewModel(
    private val repository: MovieDetailsRepository,
    private val isConnection: Boolean,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "CoroutineExceptionHandler got $exception")
    } + SupervisorJob()

    private val _detailsData = MutableLiveData<MovieDetailsResponse>()
    val detailsData: LiveData<MovieDetailsResponse> = _detailsData

    private val _castData = MutableLiveData<List<CastItem>>()
    val castData: LiveData<List<CastItem>> = _castData

    private val _dataCalendar = MutableLiveData<Intent>()
    val dataCalendar: LiveData<Intent> = _dataCalendar

    val loading = mutableStateOf(false)

    fun downloadDetailsData(id: Int) {
        viewModelScope.launch(exceptionHandler) {
            withContext(Dispatchers.IO){
                loading.value = true
                downloadDataFromDB(id = id)
                val resultMovieDetails = repository.downloadDetailsInfoForMovie(movieId = id)
                val resultCasts = repository.downloadCredits(movieId = id).cast
                resultCasts?.forEach {
                    it.movieId = id
                }
                _detailsData.postValue(resultMovieDetails)
                _castData.postValue(resultCasts ?: emptyList())
                saveDataDB(response = resultMovieDetails, credits = resultCasts ?: emptyList())
                loading.value = false
            }
        }
    }

    private suspend fun downloadDataFromDB(id: Int) {
        _detailsData.postValue(repository.getAllInfoMovie(id = id).toMovieDetailsResponse())
        _castData.postValue(repository.getAllCredits(id = id))
    }

    private suspend fun saveDataDB(response: MovieDetailsResponse, credits: List<CastItem>) {
        repository.insertMovieDetails(movie = response.toMovieDetailsEntity())
        repository.insertCredits(credits = credits)
    }

    fun writeDataCalendar(
        year: Int,
        month: Int,
        date: Int,
        hourOfDay: Int,
        minute: Int,
        movie: MovieDetailsResponse
    ) {
        viewModelScope.launch(exceptionHandler) {
            withContext(Dispatchers.IO) {
                val calID: Long = 3
                val startMillis: Long = Calendar.getInstance().run {
                    set(year, month, date, hourOfDay, minute)
                    timeInMillis
                }

                val endMillis: Long = Calendar.getInstance().run {
                    val (hoursOfRuntime, minuteOfRuntime) = runtimeMovieToHoursAndMinute(
                        runtimeMovie = movie.runtime ?: 0
                    )
                    set(year, month, date, hourOfDay + hoursOfRuntime, minute + minuteOfRuntime)
                    timeInMillis
                }
                val insertCalendarIntent = Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
                    .putExtra(CalendarContract.Events.TITLE, movie.title)
                    .putExtra(CalendarContract.Events.DESCRIPTION, movie.overview)
                    .putExtra(CalendarContract.Events.CALENDAR_ID, calID)

                _dataCalendar.postValue(insertCalendarIntent)

            }
        }
    }

    private fun runtimeMovieToHoursAndMinute(runtimeMovie: Int): Pair<Int, Int> {
        val hours = runtimeMovie / 60
        val minute = runtimeMovie % 60

        return Pair(first = hours, second = minute)
    }

    class Factory(
        private val repository: MovieDetailsRepository,
        private val isConnection: Boolean,
    ) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MovieDetailsViewModel(
                repository = repository,
                isConnection = isConnection,
            ) as T
        }
    }

    companion object {
        private const val TAG = "MovieDetailViewModel"
    }
}