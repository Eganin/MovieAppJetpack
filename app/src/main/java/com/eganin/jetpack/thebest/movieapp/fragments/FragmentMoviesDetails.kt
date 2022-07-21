package com.eganin.jetpack.thebest.movieapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.adapters.ActorAdapter
import com.eganin.jetpack.thebest.movieapp.data.models.Movie

class FragmentMoviesDetails : Fragment() {

    private val movie: Movie by lazy { arguments?.get(SAVE_MOVIE_DATA_KEY) as Movie }

    private val actorsAdapter = ActorAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI(view: View){
        setupRecyclerView(view = view)
    }

    private fun setupRecyclerView(view: View) {
        view.findViewById<RecyclerView>(R.id.actors_recycler_view).apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = actorsAdapter
            actorsAdapter.bindActors(
                actors = listOf()
            )
        }
    }

    companion object {

        private const val SAVE_MOVIE_DATA_KEY = "SAVE_MOVIE_DATA_KEY"

        fun newInstance(movieDetails: Movie): FragmentMoviesDetails {
            val args = Bundle()
            args.putParcelable(SAVE_MOVIE_DATA_KEY,movieDetails)
            return FragmentMoviesDetails().also { it.arguments = args }
        }
    }

}