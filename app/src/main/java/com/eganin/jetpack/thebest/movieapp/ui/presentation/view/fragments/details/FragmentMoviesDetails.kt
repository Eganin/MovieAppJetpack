package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.databinding.FragmentMovieDetailBinding
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.MoviesApi.Companion.BASE_IMAGE_URL
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.MoviesApi.Companion.BASE_IMAGE_URL_BACKDROP
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.CastItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.MovieDetailsResponse
import com.eganin.jetpack.thebest.movieapp.ui.presentation.utils.downloadImage
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.BaseFragment
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.MovieDetailsActivity.Companion.SAVE_MOVIE_DATA_KEY


class FragmentMoviesDetails : BaseFragment() {

    private val idMovie: Int? by lazy { arguments?.getInt(SAVE_MOVIE_DATA_KEY) }
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val actorsAdapter = ActorAdapter()
    private var viewModel: MovieDetailsViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view = view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel =
            (requireActivity().application as MovieApp).myComponent.getMoviesDetailsRepository(
                fragment = this
            )
    }

    override fun onDetach() {
        super.onDetach()
        viewModel = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupUI(view: View) {
        setupRecyclerView()
        setupListeners()
        observeData()
        idMovie?.let { viewModel?.downloadDetailsData(id = it) }
    }

    private fun observeData() {
        viewModel?.detailsData?.observe(this.viewLifecycleOwner, this::updateInfoMovie)
        viewModel?.castData?.observe(this.viewLifecycleOwner, this::updateAdapterActors)
        viewModel?.stateData?.observe(this.viewLifecycleOwner){
            setState(state = it, progressBar = binding.progressBarDetails)
        }
    }

    private fun updateInfoMovie(response: MovieDetailsResponse) {
        with(binding) {
            downloadImage(
                link = BASE_IMAGE_URL_BACKDROP + response.backdropPath,
                imageView = backgroundImage,
                context = requireContext()
            )
            adultTv.text = if (response.adult == true) "18+" else "12+"
            titleMovie.text = response.title
            tagLine.text = response.genres?.joinToString(separator = ",") { it.name ?: "" }
            countReviews.text = "${response.voteCount} REVIEWS"
            storylineDescription.text = response.overview
            response.voteAverage?.let { bindStars(rating = (it / 2).toInt()) }
        }
    }

    private fun updateAdapterActors(listActors: List<CastItem>) =
        actorsAdapter.bindActors(actors = listActors)

    private fun setupListeners() {
        binding.backBtb?.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.backBtbArrow?.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        binding.actorsRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = actorsAdapter
        }
    }

    private fun bindStars(rating: Int) {
        val listOfRating = listOf(
            binding.oneStar,
            binding.twoStar,
            binding.threeStar,
            binding.fourStar,
            binding.fiveStar,
        )
        for (i in 0 until rating) {
            listOfRating[i].setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_star_icon
                )
            )
        }
    }
}