package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.adapters.MovieAdapter
import com.eganin.jetpack.thebest.movieapp.ui.presentation.routing.Router
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details.FragmentMoviesDetails

class MovieDetailsActivity : AppCompatActivity(), Router, MovieAdapter.OnClickPoster {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun openMovieList() =
        openNewFragment {
            navigate(R.id.fragmentMoviesList)
        }

    override fun openMovieDetails(movieId: Int) {
        val bundle = bundleOf(SAVE_MOVIE_DATA_KEY to movieId)
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

    override fun clickPoster(idMovie: Int) {
        openMovieDetails(movieId = idMovie)
    }

    companion object {
        const val SAVE_MOVIE_DATA_KEY = "SAVE_MOVIE_DATA_KEY"
    }

}