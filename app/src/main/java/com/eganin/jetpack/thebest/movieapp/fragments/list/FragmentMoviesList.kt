package com.eganin.jetpack.thebest.movieapp.fragments.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.adapters.MovieAdapter
import com.eganin.jetpack.thebest.movieapp.common.ViewModelFactory
import com.eganin.jetpack.thebest.movieapp.data.models.Movie
import com.eganin.jetpack.thebest.movieapp.data.models.loadMovies
import com.eganin.jetpack.thebest.movieapp.databinding.FragmentMoviesListBinding
import com.eganin.jetpack.thebest.movieapp.utils.getColumnCountUtils
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*

class FragmentMoviesList : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!
    private val movieAdapter = MovieAdapter()
    private val viewModel: MoviesListViewModel by viewModels { ViewModelFactory(context = requireContext()) }

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
        setupRecyclerView()
        observeData()
        viewModel.downloadMoviesList()
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

    private fun observeData() {
        viewModel.moviesData.observe(this.viewLifecycleOwner) {
            movieAdapter.bindMovies(movies = it)
        }

        viewModel.stateData.observe(this.viewLifecycleOwner,this::setState)
    }

    private fun setState(state: MoviesListViewModel.State) {
        when (state) {
            MoviesListViewModel.State.Default -> setLoading(loading = true)
            MoviesListViewModel.State.Error -> showSnackBar(
                textMessage = getString(R.string.error_data_loading_snckbar_message)
            )
            MoviesListViewModel.State.Loading -> setLoading(loading = true)
            MoviesListViewModel.State.Success -> setLoading(loading = false)
        }
    }

    private fun setLoading(loading: Boolean) {
        if (loading) {
            binding.progressBarMoviesList.visibility = View.VISIBLE
        } else {
            binding.progressBarMoviesList.visibility = View.GONE
        }
    }

    private fun showSnackBar(textMessage: String) {
        view?.let { Snackbar.make(it, textMessage, Snackbar.LENGTH_LONG) }
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


    private fun setupRecyclerView() {
        binding.moviesRecyclerView.apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                getColumnCountUtils(display = activity?.windowManager?.defaultDisplay)
            )
            adapter = movieAdapter
        }
    }
}