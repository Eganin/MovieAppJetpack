package com.eganin.jetpack.thebest.movieapp.domain.data.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.Movie
import com.eganin.jetpack.thebest.movieapp.domain.data.notifications.MovieNotificationsManager
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.list.MovieRepository
import com.eganin.jetpack.thebest.movieapp.domain.data.utils.toMovieEntity
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list.TypeMovies
import kotlinx.coroutines.*
import javax.inject.Inject

class MyWorkerMovie @Inject constructor(
    context: Context,
    params: WorkerParameters,
    private val movieRepository: MovieRepository,
    private val notificationsManager: MovieNotificationsManager,
) : Worker(context, params) {

    private val coroutineScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob() + CoroutineExceptionHandler { _, exception ->
            Log.e(TAG, "$exception from workmanager")
        })

    override fun doWork(): Result {
        Log.d("EEE", "WORKER")

        return try {
            coroutineScope.launch {
                val responseMovies = movieRepository.downloadMovies(
                    page = 1,
                    typeMovies = TypeMovies.POPULAR
                ).results

                val genres = movieRepository.downloadGenres()
                // удаляем старые фильмы из БД
                movieRepository.deleteAllMovies()
                movieRepository.insertMovies(movies = responseMovies.map { it.toMovieEntity(genres = genres) })
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
    private fun getTorRatedMovie(listMovie: List<Movie>) = listMovie.maxByOrNull { it.voteAverage }

    companion object {
        private const val TAG = "MyWorkerMovie"
    }
}