package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.databinding.FragmentSearchBinding
import com.eganin.jetpack.thebest.movieapp.ui.presentation.utils.getColumnCountUtils
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.adapters.MovieAdapter
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.BaseFragment
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.MoviesListViewModel
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.MovieDetailsActivity

class FragmentSearch : BaseFragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var viewModel: MoviesListViewModel? = null
    private var movieAdapter: MovieAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel =
            (requireActivity().application as MovieApp).myComponent.getMoviesViewModelForActivity(
                activity = (requireActivity() as MovieDetailsActivity)
            )
        movieAdapter = MovieAdapter(viewModel!!)
        if (context is MovieAdapter.OnClickPoster) {
            movieAdapter?.listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        movieAdapter?.listener = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        setupRecyclerView()
        observeData()
        binding.btnSearch.setOnClickListener {
            viewModel?.downloadSearchMoviesList(query = binding.searchInput.text.toString())
            binding.placeholder.visibility = View.INVISIBLE
        }
    }

    private fun observeData() {
        viewModel?.stateData?.observe(this.viewLifecycleOwner) {
            setState(state = it, progressBar = binding.searchProgressBar)
        }
        viewModel?.moviesData?.observe(this.viewLifecycleOwner) {
            movieAdapter?.bindMovies(movies = it)
        }
    }

    private fun setupRecyclerView() {
        movieAdapter?.clearMovies()
        binding.recyclerViewSearchMovies.apply {
            layoutManager =
                GridLayoutManager(
                    requireContext(),
                    getColumnCountUtils(display = activity?.windowManager?.defaultDisplay)
                )
            adapter = movieAdapter
        }
    }

}