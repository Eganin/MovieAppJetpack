package com.eganin.jetpack.thebest.movieapp.presentation.view.fragments.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.presentation.view.adapters.MovieAdapter
import com.eganin.jetpack.thebest.movieapp.databinding.FragmentMoviesListBinding
import com.eganin.jetpack.thebest.movieapp.presentation.utils.getColumnCountUtils
import com.google.android.material.snackbar.Snackbar

class FragmentMoviesList : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!
    private var viewModel: MoviesListViewModel?=null
    private  var movieAdapter: MovieAdapter?=null

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

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel =
            (requireActivity().application as MovieApp).myComponent.getMoviesViewModel(fragment = this)
        if (context is MovieAdapter.OnClickPoster)
            movieAdapter?.listener = context

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        movieAdapter?.listener = null
    }

    private fun observeData() {
        viewModel?.moviesData?.observe(this.viewLifecycleOwner) {
            movieAdapter?.bindMovies(movies = it.results)
        }

        viewModel?.stateData?.observe(this.viewLifecycleOwner, this::setState)

        viewModel?.changeMovies?.observe(this.viewLifecycleOwner, this::setListMovies)
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

    private fun setupUI() {
        movieAdapter= MovieAdapter(moviesListViewModel = viewModel!!)
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            viewModel?.changeMoviesList(idPage = item.itemId) ?: false
        }
        setupRecyclerView()
        observeData()
        viewModel?.downloadMoviesList(typeMovies = DEFAULT_MOVIES)
    }

    private fun setListMovies(value: String) {
        movieAdapter?.clearMovies()
        binding.listType.text = value
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

    companion object {
        private val DEFAULT_MOVIES = TypeMovies.POPULAR
    }
}