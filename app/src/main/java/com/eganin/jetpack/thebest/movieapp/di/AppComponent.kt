package com.eganin.jetpack.thebest.movieapp.di

import com.eganin.jetpack.thebest.movieapp.BuildConfig
import com.eganin.jetpack.thebest.movieapp.data.models.network.MoviesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppComponent {

    init{
        val api = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApi::class.java)
    }

}