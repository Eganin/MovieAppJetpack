package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.details

import android.content.Intent
import android.provider.CalendarContract
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.eganin.jetpack.thebest.movieapp.base.EventHandler
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.CastItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.MovieDetailsResponse
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.details.MovieDetailsRepository
import com.eganin.jetpack.thebest.movieapp.domain.data.utils.toMovieDetailsEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.utils.toMovieDetailsResponse
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.details.models.DetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieDetailsRepository,
) : ViewModel(), EventHandler<DetailsState> {

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "CoroutineExceptionHandler got $exception")
        _detailsState.postValue(DetailsState.Error)
    } + SupervisorJob()

    // хранение данных о фильие
    private val _detailsData = MutableLiveData<MovieDetailsResponse>()
    val detailsData: LiveData<MovieDetailsResponse> = _detailsData

    // список актеров
    private val _castData = MutableLiveData<List<CastItem>>()
    val castData: LiveData<List<CastItem>> = _castData

    // livedata c Intent для открытия каледнаря
    private val _dataCalendar = MutableLiveData<Intent>()
    val dataCalendar: LiveData<Intent> = _dataCalendar

    private val _detailsState: MutableLiveData<DetailsState> = MutableLiveData(DetailsState.Display)
    val detailsState: LiveData<DetailsState> = _detailsState

    override fun obtainEvent(event: DetailsState) {
        _detailsState.postValue(event)
        if (event is DetailsState.Loading) downloadDetailsData(event.id)
    }

    fun downloadDetailsData(id: Int) {
        viewModelScope.launch(exceptionHandler) {
            withContext(Dispatchers.IO) {
                downloadDataFromDB(id = id)
                val resultMovieDetails = repository.downloadDetailsInfoForMovie(movieId = id)
                val resultCasts = repository.downloadCredits(movieId = id).cast
                if (resultCasts.isEmpty() || resultMovieDetails == null) {
                    _detailsState.postValue(DetailsState.NoInfo)
                }
                /*
                добавляем в список актеров id-фильма,
                чтобы сохранить в БД и поэтому id найти нужный список
                 */
                resultCasts.forEach {
                    it.movieId = id
                }
                resultMovieDetails?.let { movieDetails ->
                    _detailsData.postValue(movieDetails)
                    _castData.postValue(resultCasts)
                    saveDataDB(response = movieDetails, credits = resultCasts)
                }
                _detailsState.postValue(DetailsState.Display)
            }
        }
    }

    private suspend fun downloadDataFromDB(id: Int) {
        _detailsData.postValue(repository.getAllInfoMovie(id = id)?.toMovieDetailsResponse())
        _castData.postValue(repository.getAllCredits(id = id))
    }

    private suspend fun saveDataDB(response: MovieDetailsResponse, credits: List<CastItem>) {
        repository.insertMovieDetails(movie = response.toMovieDetailsEntity())
        repository.insertCredits(credits = credits)
    }

    /*
    метод записывает нужные данные для напоминания о просмотре фильма
     */
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
                val calID: Long = 69
                // время начала событияя для календаря
                val startMillis: Long = Calendar.getInstance().run {
                    set(year, month, date, hourOfDay, minute)
                    timeInMillis
                }
                // время окончания событияя для календаря
                val endMillis: Long = Calendar.getInstance().run {
                    val (hoursOfRuntime, minuteOfRuntime) = runtimeMovieToHoursAndMinute(
                        runtimeMovie = movie.runtime
                    )
                    set(year, month, date, hourOfDay + hoursOfRuntime, minute + minuteOfRuntime)
                    timeInMillis
                }
                // create Intent
                val insertCalendarIntent = Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
                    .putExtra(CalendarContract.Events.TITLE, movie.title)
                    .putExtra(CalendarContract.Events.DESCRIPTION, movie.overview)
                    .putExtra(CalendarContract.Events.CALENDAR_ID, calID)

                // потом в CalendarView запускаем calendar activity
                _dataCalendar.postValue(insertCalendarIntent)

            }
        }
    }

    /*
        Метод возвращает длительность фильма
     */
    private fun runtimeMovieToHoursAndMinute(runtimeMovie: Int): Pair<Int, Int> {
        val hours = runtimeMovie / 60
        val minute = runtimeMovie % 60

        return Pair(first = hours, second = minute)
    }

    companion object {
        private const val TAG = "MovieDetailViewModel"
    }

}