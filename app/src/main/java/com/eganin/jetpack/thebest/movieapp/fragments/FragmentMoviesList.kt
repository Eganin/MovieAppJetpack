package com.eganin.jetpack.thebest.movieapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.adapters.MovieAdapter
import com.eganin.jetpack.thebest.movieapp.data.models.Actor
import com.eganin.jetpack.thebest.movieapp.data.models.Genre
import com.eganin.jetpack.thebest.movieapp.data.models.Movie
import com.eganin.jetpack.thebest.movieapp.data.models.loadMovies
import com.eganin.jetpack.thebest.movieapp.databinding.FragmentMoviesListBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import kotlinx.parcelize.RawValue

class FragmentMoviesList : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!

    private val superExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "Failed", exception)
    }

    private val movieAdapter = MovieAdapter()
    private val uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob() + superExceptionHandler)
    private var moviesData: List<Movie> = listOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        downloadData()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MovieAdapter.OnClickPoster)
            movieAdapter.listener = context

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        movieAdapter.listener = null
    }

    private fun downloadData() {
        uiScope.launch {
            val differed = withContext(uiScope.coroutineContext) {
                moviesData = loadMovies(requireContext())
            }
            setupRecyclerView(dataMovies = moviesData)
        }
    }

    //Plug
    private fun setupUI() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {
                    true
                }
                R.id.page_2 -> {

                    true
                }
                R.id.page_3 -> {

                    true
                }
                R.id.page_4 -> {

                    true
                }
                R.id.page_5 -> {

                    true
                }

                else -> false
            }
        }
    }


    private fun setupRecyclerView(dataMovies: List<Movie>) {
        binding.moviesRecyclerView.apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                arguments?.getInt(COLUMN_COUNT_SAVE) ?: DEFAULT_COLUMN_COUNT
            )
            adapter = movieAdapter
            movieAdapter.bindMovies(movies = dataMovies)
        }
    }

    companion object {

        private val TAG = "FRAGMENT_MOVIES_LIST"

        fun newInstance(columnCount: Int = DEFAULT_COLUMN_COUNT): FragmentMoviesList {
            val args = Bundle()
            args.putInt(COLUMN_COUNT_SAVE, columnCount)
            return FragmentMoviesList().also { it.arguments = args }
        }

        private const val COLUMN_COUNT_SAVE = "countColumn"
        private const val DEFAULT_COLUMN_COUNT = 2
    }
}