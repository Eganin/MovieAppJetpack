package com.eganin.jetpack.thebest.movieapp.domain.data.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.Movie
import com.eganin.jetpack.thebest.movieapp.domain.data.utils.toMovieEntity
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list.TypeMovies
import kotlinx.coroutines.*

class MyWorkerMovie(private val context: Context, params: WorkerParameters) :
    Worker(context, params) {

    private val coroutineScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob() + CoroutineExceptionHandler { _, exception ->
            Log.e(TAG, "$exception from workmanager")
        })

    override fun doWork(): Result {
        Log.d("EEE","WORKER")
        val componentDi = (context.applicationContext as MovieApp).myComponent
        val repository = componentDi.movieRepository

        val notificationsManager = componentDi.notificationManager

        return try {
            coroutineScope.launch {
                val responseMovies = repository.downloadMovies(
                    page = 1,
                    typeMovies = TypeMovies.POPULAR
                )?.results ?: emptyList()

                val genres = repository.downloadGenres()
                // удаляем старые фильмы из БД
                repository.deleteAllMovies()
                repository.insertMovies(movies = responseMovies.map { it.toMovieEntity(genres = genres) })
                // показываем уведомление
                getTorRatedMovie(listMovie = responseMovies)?.let {
                    notificationsManager.showNotification(movie = it)
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    /*
    Метод возвращает фильм с наивысшым рейтингом
     */
    private fun getTorRatedMovie(listMovie: List<Movie>) = listMovie.maxByOrNull { it.voteAverage!! }

    companion object {
        private const val TAG = "MyWorkerMovie"
    }
}