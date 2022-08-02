package com.eganin.jetpack.thebest.movieapp.domain.data.network

import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.MoviesApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

object RetrofitModule {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val client = OkHttpClient()
        .newBuilder()
        .addInterceptor(
            interceptor = HttpLoggingInterceptor()
                .setLevel(level = HttpLoggingInterceptor.Level.BODY)
        )
        .build()

    @OptIn(ExperimentalSerializationApi::class)
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .client(client)
        .build()

    val api: MoviesApi = retrofit.create()
}