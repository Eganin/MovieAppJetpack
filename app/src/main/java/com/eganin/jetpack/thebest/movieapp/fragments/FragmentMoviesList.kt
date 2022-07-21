package com.eganin.jetpack.thebest.movieapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.adapters.MovieAdapter
import com.eganin.jetpack.thebest.movieapp.data.models.Movie
import com.eganin.jetpack.thebest.movieapp.data.models.loadMovies
import com.eganin.jetpack.thebest.movieapp.databinding.FragmentMoviesListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentMoviesList : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!

    private val movieAdapter = MovieAdapter()
    private val uiScope = CoroutineScope(Dispatchers.Main)
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
        fun newInstance(columnCount: Int = DEFAULT_COLUMN_COUNT): FragmentMoviesList {
            val args = Bundle()
            args.putInt(COLUMN_COUNT_SAVE, columnCount)
            return FragmentMoviesList().also { it.arguments = args }
        }

        private const val COLUMN_COUNT_SAVE = "countColumn"
        private const val DEFAULT_COLUMN_COUNT = 2
    }
}