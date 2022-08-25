package com.eganin.jetpack.thebest.movieapp.domain.data.network

import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("configuration")
    suspend fun getConfiguration(
        @Query(API_KEY) apiKey: String = API_KEY_VALUE,
    ): ConfigurationResponse

    @GET("movie/now_playing")
    suspend fun getMoviesNowPlaying(
        @Query(API_KEY) apiKey: String = API_KEY_VALUE,
        @Query(LANGUAGE) language: String = LanguageQuery.ENGLISH.field,
        @Query(PAGE) page: Int,
    ): MovieResponse

    @GET("movie/popular")
    suspend fun getMoviesPopular(
        @Query(API_KEY) apiKey: String = API_KEY_VALUE,
        @Query(LANGUAGE) language: String = LanguageQuery.ENGLISH.field,
        @Query(PAGE) page: Int,
    ): MovieResponse

    @GET("movie/top_rated")
    suspend fun getMoviesTopRated(
        @Query(API_KEY) apiKey: String = API_KEY_VALUE,
        @Query(LANGUAGE) language: String = LanguageQuery.ENGLISH.field,
        @Query(PAGE) page: Int,
    ): MovieResponse

    @GET("movie/upcoming")
    suspend fun getMoviesUpComing(
        @Query(API_KEY) apiKey: String = API_KEY_VALUE,
        @Query(LANGUAGE) language: String = LanguageQuery.ENGLISH.field,
        @Query(PAGE) page: Int,
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetailsUsingId(
        @Path(MOVIE_ID) movieId: Int,
        @Query(API_KEY) apiKey: String = API_KEY_VALUE,
        @Query(LANGUAGE) language: String = LanguageQuery.ENGLISH.field,
    ): MovieDetailsResponse

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query(API_KEY) apiKey: String = API_KEY_VALUE,
        @Query(LANGUAGE) language: String = LanguageQuery.ENGLISH.field,
    ):GenresResponse

    @GET("search/movie")
    suspend fun getSearchMovie(
        @Query(API_KEY) apiKey: String = API_KEY_VALUE,
        @Query(LANGUAGE) language: String = LanguageQuery.ENGLISH.field,
        @Query(QUERY) queryText: String,
        @Query(PAGE) page: Int,
    ): MovieResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getCreditsUsingId(
        @Path(MOVIE_ID) movieId: Int,
        @Query(API_KEY) apiKey: String = API_KEY_VALUE,
        @Query(LANGUAGE) language: String = LanguageQuery.ENGLISH.field,
    ):CreditsMovies

    companion object {
        private const val API_KEY = "api_key"
        private const val LANGUAGE = "language"
        private const val MOVIE_ID = "movie_id"
        private const val PAGE = "page"
        private const val QUERY = "query"
        private const val API_KEY_VALUE = "e3e1fa04065d06e42820f0ee5b375a0d"
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w300"
        const val BASE_IMAGE_URL_BACKDROP = "https://image.tmdb.org/t/p/w500"
    }
}

enum class LanguageQuery(val field: String) {
    RUSSIAN(field = "ru-RU"),
    ENGLISH(field = "en-US"),
}