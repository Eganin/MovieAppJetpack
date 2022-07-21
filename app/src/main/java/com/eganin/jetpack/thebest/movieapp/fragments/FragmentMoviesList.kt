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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentMoviesList : Fragment() {

    private val movieAdapter = MovieAdapter()
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private var moviesData : List<Movie> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        downloadData(view = view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MovieAdapter.OnClickPoster)
            movieAdapter.listener = context

    }

    override fun onDetach() {
        super.onDetach()
        movieAdapter.listener = null
    }

    private fun downloadData(view:View){
        uiScope.launch {
            val differed = withContext(uiScope.coroutineContext) {
                moviesData = loadMovies(requireContext())
            }
            setupRecyclerView(view=view, dataMovies = moviesData)
        }
    }


    private fun setupRecyclerView(view: View,dataMovies : List<Movie>) {
        view.findViewById<RecyclerView>(R.id.movies_recycler_view).apply {
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