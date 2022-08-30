package com.eganin.jetpack.thebest.movieapp.di

import android.app.Application
import com.eganin.jetpack.thebest.movieapp.domain.data.database.MovieDatabase
import com.eganin.jetpack.thebest.movieapp.domain.data.notifications.MovieNotificationsManager
import com.eganin.jetpack.thebest.movieapp.domain.data.notifications.Notifications
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list.TypeMovies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(appContext: Application): MovieDatabase {
        return MovieDatabase.create(appContext)
    }

    @Provides
    @Singleton
    @Named("language")
    fun provideLanguage(): String {
        return Locale.getDefault().language
    }

    @Provides
    @Singleton
    fun provideNotificationManager(appContext: Application): MovieNotificationsManager {
        return MovieNotificationsManager(context = appContext)
    }
}