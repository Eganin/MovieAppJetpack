package com.eganin.jetpack.thebest.movieapp.domain.data.repositories.list

import android.util.Log
import com.eganin.jetpack.thebest.movieapp.domain.data.database.MovieDatabase
import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.FavouriteEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.MovieEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.network.RetrofitModule
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.GenresItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.MovieResponse
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list.TypeMovies
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named


class MovieRepositoryImpl @Inject constructor(
    @Named("language") val language: String,
    database: MovieDatabase
) : MovieRepository {

    private val movieDao = database.movieDao
    private val favouriteDao = database.favouriteDao

    override suspend fun downloadMovies(page: Int, typeMovies: TypeMovies): MovieResponse =
        withContext(defaultDispatcher) {
            // загружаем нужный тип фильмов в зависимости от входных данных
            when (typeMovies) {
                TypeMovies.TOP_RATED -> RetrofitModule.api.getMoviesTopRated(
                    page = page,
                    language = language
                )
                TypeMovies.POPULAR -> RetrofitModule.api.getMoviesPopular(
                    page = page,
                    language = language
                )
                TypeMovies.NOW_PLAYING -> RetrofitModule.api.getMoviesNowPlaying(
                    page = page,
                    language = language
                )
                else -> RetrofitModule.api.getMoviesUpComing(
                    page = page,
                    language = language
                )
            }
        }

    override suspend fun downloadSearchMovies(page: Int, query: String): MovieResponse =
        withContext(defaultDispatcher) {
            RetrofitModule.api.getSearchMovie(queryText = query, page = page)
        }

    override suspend fun downloadGenres(): List<GenresItem> = withContext(defaultDispatcher) {
        RetrofitModule.api.getGenres().genres
    }

    /*
    метод возвращает все сохраненные фильмы из БД
     */
    override suspend fun getAllMovies(): List<MovieEntity> = withContext(defaultDispatcher) {
        val res = movieDao.getAllMovies()
        res.ifEmpty {
            emptyList()
        }
    }

    /*
    записываем фильмы определенной категории в БД
     */
    override suspend fun insertMovies(movies: List<MovieEntity>) = withContext(defaultDispatcher) {
        movieDao.insertMovies(movies = movies)
    }

    /*
    удаляем все фильмы щагруженные в БД
     */
    override suspend fun deleteAllMovies() = withContext(defaultDispatcher) {
        movieDao.deleteAllMovies()
    }

    /*
    возвращает избранный фильм по id
     */
    override suspend fun getFavouriteMovieUsingID(id: Int): FavouriteEntity? =
        withContext(defaultDispatcher) {
            favouriteDao.getFavouriteMovieUsingID(id = id)
        }

    /*
    вставляем избранный фильм в БД
     */
    override suspend fun insertFavouriteMovie(favouriteMovie: FavouriteEntity) =
        withContext(defaultDispatcher) {
            favouriteDao.insertFavouriteMovie(favouriteMovie = favouriteMovie)
        }

    /*
    метод очищаеи все изьраннеы фильмы
     */
    override suspend fun deleteAllFavouriteMovie() = withContext(defaultDispatcher) {
        favouriteDao.deleteAllFavouriteMovie()
    }

    /*
    метод удаляет избранный фильм по id
     */
    override suspend fun deleteFavouriteMovieUsingID(id: Int) = withContext(defaultDispatcher) {
        favouriteDao.deleteFavouriteMovieUsingID(id = id)
    }

}