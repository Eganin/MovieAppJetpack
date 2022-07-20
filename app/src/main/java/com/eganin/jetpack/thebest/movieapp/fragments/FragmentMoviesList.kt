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
import com.eganin.jetpack.thebest.movieapp.data.models.MoviesDataSource

class FragmentMoviesList : Fragment() {

    private val movieAdapter = MovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)
        .also { setupRecyclerView(view = it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setupRecyclerView(view = view)
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


    private fun setupRecyclerView(view: View) {
        view.findViewById<RecyclerView>(R.id.movies_recycler_view).apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                arguments?.getInt(COLUMN_COUNT_SAVE) ?: DEFAULT_COLUMN_COUNT
            )
            adapter = movieAdapter
            movieAdapter.bindMovies(newMovies = MoviesDataSource.getMovies())
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