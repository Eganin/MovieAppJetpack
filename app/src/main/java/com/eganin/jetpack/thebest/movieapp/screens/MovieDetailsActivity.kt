package com.eganin.jetpack.thebest.movieapp.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.eganin.jetpack.thebest.movieapp.fragments.FragmentMoviesDetails
import com.eganin.jetpack.thebest.movieapp.fragments.FragmentMoviesList
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.adapters.MovieAdapter
import com.eganin.jetpack.thebest.movieapp.data.models.Movie
import com.eganin.jetpack.thebest.movieapp.fragments.FragmentMoviesListDirections
import com.eganin.jetpack.thebest.movieapp.routing.Router
import com.eganin.jetpack.thebest.movieapp.utils.getColumnCountUtils

class MovieDetailsActivity : AppCompatActivity(), Router, MovieAdapter.OnClickPoster {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun openMovieList() =
        openNewFragment {
            navigate(R.id.fragmentMoviesList)
        }

    override fun openMovieDetails(movieDetails: Movie) {
        val bundle = bundleOf(SAVE_MOVIE_DATA_KEY to movieDetails)
        openNewFragment {
            navigate(R.id.fragmentMoviesDetails, bundle)
        }

    }

    private fun openNewFragment(
        transaction: NavController.() -> Unit,
    ) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.apply(transaction)
    }

    override fun clickPoster(movie: Movie) =
        openMovieDetails(movieDetails = movie)

    companion object {
        const val SAVE_MOVIE_DATA_KEY = "SAVE_MOVIE_DATA_KEY"
    }

}