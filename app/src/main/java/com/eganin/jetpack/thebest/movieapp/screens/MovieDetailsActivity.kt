package com.eganin.jetpack.thebest.movieapp.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.eganin.jetpack.thebest.movieapp.fragments.FragmentMoviesDetails
import com.eganin.jetpack.thebest.movieapp.fragments.FragmentMoviesList
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.adapters.MovieAdapter
import com.eganin.jetpack.thebest.movieapp.data.models.Movie
import com.eganin.jetpack.thebest.movieapp.routing.Router
import com.eganin.jetpack.thebest.movieapp.utils.getColumnCountUtils

class MovieDetailsActivity : AppCompatActivity(), Router, MovieAdapter.OnClickPoster {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            openMovieList()
        }
    }

    override fun openMovieList() =
        openNewFragment {
            replace(
                R.id.main_container_fragment,
                FragmentMoviesList.newInstance(columnCount = getColumnCountUtils(display = windowManager.defaultDisplay))
            )
        }


    override fun openMovieDetails(movieDetails: Movie) =
        openNewFragment {
            add(
                R.id.main_container_fragment,
                FragmentMoviesDetails.newInstance(movieDetails = movieDetails)
            )
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

    override fun clickPoster(movie: Movie) = openMovieDetails(movieDetails = movie)
}