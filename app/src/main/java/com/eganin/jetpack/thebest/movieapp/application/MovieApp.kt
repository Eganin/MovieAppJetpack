package com.eganin.jetpack.thebest.movieapp.application

import android.app.Application
import com.eganin.jetpack.thebest.movieapp.di.AppComponent

class MovieApp : Application() {

    val myComponent : AppComponent by lazy { AppComponent(this) }
}