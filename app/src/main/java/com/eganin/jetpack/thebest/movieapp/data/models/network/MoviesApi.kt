package com.eganin.jetpack.thebest.movieapp.data.models.network

import com.eganin.jetpack.thebest.movieapp.BuildConfig
import com.eganin.jetpack.thebest.movieapp.data.models.network.entities.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("configuration")
    suspend fun getConfiguration(
        @Query(API_KEY) apiKey: String = BuildConfig.API_KEY,
    ): ConfigurationResponse

    @GET("movie/now_playing")
    suspend fun getMoviesNowPlaying(
        @Query(API_KEY) apiKey: String = BuildConfig.API_KEY,
        @Query(LANGUAGE) language: String = LanguageQuery.ENGLISH.field,
        @Query(PAGE) page: Int,
    ): PopularAndNowPlayingResponse

    @GET("movie/popular")
    suspend fun getMoviesPopular(
        @Query(API_KEY) apiKey: String = BuildConfig.API_KEY,
        @Query(LANGUAGE) language: String = LanguageQuery.ENGLISH.field,
        @Query(PAGE) page: Int,
    ): PopularAndNowPlayingResponse

    @GET("movie/top_rated")
    suspend fun getMoviesTopRated(
        @Query(API_KEY) apiKey: String = BuildConfig.API_KEY,
        @Query(LANGUAGE) language: String = LanguageQuery.ENGLISH.field,
        @Query(PAGE) page: Int,
    ): TopRatedAndUpComingAndSearchResponse

    @GET("movie/upcoming")
    suspend fun getMoviesUpComing(
        @Query(API_KEY) apiKey: String = BuildConfig.API_KEY,
        @Query(LANGUAGE) language: String = LanguageQuery.ENGLISH.field,
        @Query(PAGE) page: Int,
    ): TopRatedAndUpComingAndSearchResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetailsUsingId(
        @Path(MOVIE_ID) movieId: Int,
        @Query(API_KEY) apiKey: String = BuildConfig.API_KEY,
        @Query(LANGUAGE) language: String = LanguageQuery.ENGLISH.field,
    ): MovieDetailsResponse

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query(API_KEY) apiKey: String = BuildConfig.API_KEY,
        @Query(LANGUAGE) language: String = LanguageQuery.ENGLISH.field,
    )

    @GET("search/movie")
    suspend fun getSearchMovie(
        @Query(API_KEY) apiKey: String = BuildConfig.API_KEY,
        @Query(LANGUAGE) language: String = LanguageQuery.ENGLISH.field,
        @Query(QUERY) queryText: String,
        @Query(PAGE) page: Int,
    ): TopRatedAndUpComingAndSearchResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getCreditsUsingId(
        @Query(API_KEY) apiKey: String = BuildConfig.API_KEY,
        @Query(LANGUAGE) language: String = LanguageQuery.ENGLISH.field,
        @Path(MOVIE_ID) movieId: Int,
    )

    companion object {
        private const val API_KEY = "api_key"
        private const val LANGUAGE = "language"
        private const val MOVIE_ID = "movie_id"
        private const val PAGE = "page"
        private const val QUERY = "query"
    }
}

enum class LanguageQuery(val field: String) {
    RUSSIAN(field = "ru-RU"),
    ENGLISH(field = "en-US"),
}