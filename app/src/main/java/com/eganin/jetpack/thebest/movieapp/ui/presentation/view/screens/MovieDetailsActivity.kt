package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.work.WorkManager
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.databinding.ActivityMainBinding
import com.eganin.jetpack.thebest.movieapp.ui.presentation.routing.Router
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details.MovieDetails
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.MovieAdapter
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
        startWorker()
        if (savedInstanceState == null) handleIntent(intent = intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { handleIntent(it) }
    }

    private fun handleIntent(intent: Intent) {
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                val id = intent.data?.lastPathSegment?.toIntOrNull()
                id?.let {
                    openMovieDetails(movieId = it, isNotification = true)
                }
            }
        }
    }


    private fun startWorker() {
        val repository = (applicationContext as MovieApp).myComponent.getWorkerRepository()
        WorkManager.getInstance(this).enqueue(repository.request)
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

        openMovieList()
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun openMovieList() {
        /*
        setContent {
            val appComponent = (LocalContext.current.applicationContext as MovieApp).myComponent
            val scaffoldState = rememberScaffoldState()

            Scaffold(scaffoldState = scaffoldState) {
                ListMovies(
                    repository = appComponent.getMovieRepository(),
                    isConnection = appComponent.connection,
                    sharedPreferences = appComponent.getSharedPreferencesMovieType(),
                    notificationsManager = appComponent.getNotificationManager(),
                    typeMovie = TypeMovies.POPULAR
                )
            }
        }

         */
    }


    override fun openMovieDetails(movieId: Int, isNotification: Boolean) {
        viewModel.firstLaunch = isNotification
        val bundle = bundleOf(SAVE_MOVIE_DATA_KEY to movieId)
        /*
        openNewFragment {
            navigate(R.id.fragmentMoviesDetails, bundle)
        }
         */
    }

    override fun openSearch() {
        /*
        openNewFragment {
            navigate(R.id.fragmentSearch)
        }

         */
    }

    private fun openNewFragment(
        transaction: NavController.() -> Unit,
    ) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.apply(transaction)
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun clickPoster(idMovie: Int) {
        /*
        setContent {
            val appComponent = (LocalContext.current.applicationContext as MovieApp).myComponent
            val scaffoldState = rememberScaffoldState()

            Scaffold(scaffoldState = scaffoldState) {
                MovieDetails(
                    id = idMovie,
                    repository = appComponent.movieDetailsRepository,
                    connection = appComponent.connection,
                    scaffoldState = scaffoldState
                )
            }
        }

         */
    }

    companion object {
        const val SAVE_MOVIE_DATA_KEY = "SAVE_MOVIE_DATA_KEY"
    }

}