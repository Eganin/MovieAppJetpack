package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.databinding.ActivityMainBinding
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.MovieAdapter
import com.eganin.jetpack.thebest.movieapp.ui.presentation.routing.Router
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.MoviesListViewModel

class MovieDetailsActivity : AppCompatActivity(), Router, MovieAdapter.OnClickPoster {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MoviesListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel =
            (this.application as MovieApp).myComponent.getMoviesViewModel(activity = this)!!
        val view = binding.root
        setContentView(view)
        setupUI()
    }

    private fun setupUI() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_5 -> {
                    openSearch()
                    viewModel.changeMoviesList(idPage = item.itemId)
                }
                else -> {
                    if (binding.bottomNavigation.menu.findItem(R.id.page_5).isChecked) {
                        openNewFragment {
                            popBackStack()
                        }
                    }
                    viewModel.changeMoviesList(idPage = item.itemId)
                }
            }
        }
    }

    override fun openMovieList() =
        openNewFragment {
            navigate(R.id.fragmentMoviesList)
        }

    override fun openMovieDetails(movieId: Int) {
        viewModel.firstLaunch = false
        val bundle = bundleOf(SAVE_MOVIE_DATA_KEY to movieId)
        openNewFragment {
            navigate(R.id.fragmentMoviesDetails, bundle)
        }
    }

    override fun openSearch() {
        openNewFragment {
            navigate(R.id.fragmentSearch)
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