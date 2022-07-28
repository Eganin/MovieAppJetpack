package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.databinding.FragmentMoviesListBinding
import com.eganin.jetpack.thebest.movieapp.ui.presentation.utils.getColumnCountUtils
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.BaseFragment
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.MovieDetailsActivity

class FragmentMoviesList : BaseFragment() {

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!
    private var viewModel: MoviesListViewModel? = null
    private var movieAdapter: MovieAdapter? = null

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
            (requireActivity().application as MovieApp).myComponent.getMoviesViewModelForActivity(
                activity = requireActivity() as MovieDetailsActivity
            )
        movieAdapter = MovieAdapter(moviesListViewModel = viewModel!!)
        if (context is MovieAdapter.OnClickPoster) {
            movieAdapter?.listener = context
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        viewModel = null
        movieAdapter?.listener = null
    }

    private fun observeData() {
        viewModel?.stateData?.observe(this.viewLifecycleOwner){
            setState(state = it, progressBar = binding.progressBarMoviesList)
        }
        viewModel?.changeMovies?.observe(this.viewLifecycleOwner, this::setListMovies)
        viewModel?.moviesData?.observe(this.viewLifecycleOwner) {
            movieAdapter?.bindMovies(movies = it)
        }
    }


    private fun setupUI() {
        viewModel?.isQueryRequest=false
        setupRecyclerView()
        observeData()
    }

    private fun setListMovies(value: String) {
        binding.listType.text = value
        viewModel?.downloadMoviesList()
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