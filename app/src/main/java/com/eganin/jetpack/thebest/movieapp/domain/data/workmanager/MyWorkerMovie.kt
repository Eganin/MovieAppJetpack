package com.eganin.jetpack.thebest.movieapp.domain.data.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.di.AppComponent
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.Movie
import com.eganin.jetpack.thebest.movieapp.ui.presentation.utils.toMovieEntity
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.TypeMovies
import kotlinx.coroutines.*

class MyWorkerMovie(private val context: Context, params: WorkerParameters) :
    Worker(context, params) {

    private val coroutineScope =
        CoroutineScope(Dispatchers.IO + Job() + CoroutineExceptionHandler { _, exception ->
            Log.e(TAG, "$exception from workmanager")
        })

    override fun doWork(): Result {
        Log.d("EEE","WORKER")
        val componentDi = (context.applicationContext as MovieApp).myComponent
        val repository = componentDi.getMovieRepository()
        val typeMovieString = componentDi.getSharedPreferencesMovieType()
            .getString(AppComponent.TOKEN_CHOICE_MOVIE, TypeMovies.POPULAR.value)

        val notificationsManager = componentDi.getNotificationManager()

        return try {
            coroutineScope.launch {
                val responseMovies = repository.downloadMovies(
                    page = 1,
                    typeMovies = getTypeMovie(value = typeMovieString ?: "Popular")
                ).results ?: emptyList()

                val genres = repository.downloadGenres() ?: emptyList()
                repository.deleteAllMovies()
                repository.insertMovies(movies = responseMovies.map { it.toMovieEntity(genres = genres) })
                notificationsManager.showNotification(movie = getTorRatedMovie(listMovie = responseMovies)!!)
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun getTorRatedMovie(listMovie: List<Movie>) = listMovie.maxByOrNull { it.voteAverage!! }


    private fun getTypeMovie(value: String) = when (value) {
        "Top Rated" -> TypeMovies.TOP_RATED
        "Popular" -> TypeMovies.POPULAR
        "Now Playing" -> TypeMovies.NOW_PLAYING
        else -> TypeMovies.UP_COMING
    }

    companion object {
        private const val TAG = "MyWorkerMovie"
    }
}