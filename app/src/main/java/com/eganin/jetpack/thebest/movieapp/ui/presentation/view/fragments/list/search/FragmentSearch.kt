package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.databinding.FragmentSearchBinding
import com.eganin.jetpack.thebest.movieapp.ui.presentation.utils.afterTextChanged
import com.eganin.jetpack.thebest.movieapp.ui.presentation.utils.getColumnCountUtils
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.MovieAdapter
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.BaseFragment
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.GridSpacingItemDecoration
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.MoviesListViewModel
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.MovieDetailsActivity
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch

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
            (requireActivity().application as MovieApp).myComponent.getMoviesViewModel(
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

    @OptIn(ObsoleteCoroutinesApi::class)
    private fun setupUI() {
        if (viewModel?.moviesSearchData?.value?.isNotEmpty()!!) {
            binding.placeholder.isVisible = false
        }
        setupRecyclerView()
        observeData()
        binding.btnSearch.setOnClickListener {
            viewModel?.downloadSearchMoviesList(query = binding.searchInput.text.toString())
            binding.placeholder.isVisible = false
        }

        binding.searchInput.afterTextChanged {
            lifecycleScope.launch {
                viewModel?.queryChannel?.send(it)
            }
        }
    }

    private fun observeData() {
        viewModel?.stateData?.observe(this.viewLifecycleOwner) {
            setState(state = it, progressBar = binding.searchProgressBar)
        }
        viewModel?.moviesSearchData?.observe(this.viewLifecycleOwner) {
            movieAdapter?.bindMovies(movies = it)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewSearchMovies.apply {
            val spanCount = getColumnCountUtils(display = activity?.windowManager?.defaultDisplay)
            layoutManager =
                GridLayoutManager(
                    requireContext(),
                    spanCount
                )
            adapter = movieAdapter
            addItemDecoration(
                GridSpacingItemDecoration(
                    spanCount = spanCount,
                    spacing = resources.getDimension(
                        R.dimen.item_dist
                    ).toInt(),
                    includeEdge = true,
                )
            )
        }
    }

}