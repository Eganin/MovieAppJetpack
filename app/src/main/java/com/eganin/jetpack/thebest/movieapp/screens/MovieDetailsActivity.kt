package com.eganin.jetpack.thebest.movieapp.screens

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentTransaction
import com.eganin.jetpack.thebest.movieapp.fragments.FragmentMoviesDetails
import com.eganin.jetpack.thebest.movieapp.fragments.FragmentMoviesList
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.routing.Router
import com.eganin.jetpack.thebest.movieapp.utils.getColumnCountUtils

class MovieDetailsActivity : AppCompatActivity(), Router {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            openMovieList()
            //openMovieDetails()
        }
    }

    override fun openMovieList() =
        openNewFragment {
            add(
                R.id.main_container_fragment,
                FragmentMoviesList.newInstance(columnCount = getColumnCountUtils(display = windowManager.defaultDisplay))
            )
        }


    override fun openMovieDetails() =
        openNewFragment {
            add(R.id.main_container_fragment, FragmentMoviesDetails.newInstance())
        }


    private fun openNewFragment(
        transaction: FragmentTransaction.() -> Unit,
    ) {
        val manager = supportFragmentManager.beginTransaction()

        manager.apply {
            transaction()
            addToBackStack(null)
            commit()
        }
    }
}