package com.eganin.jetpack.thebest.movieapp.presentation.view.fragments.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.presentation.view.adapters.ActorAdapter
import com.eganin.jetpack.thebest.movieapp.common.ViewModelFactory
import com.eganin.jetpack.thebest.movieapp.databinding.FragmentMovieDetailBinding
import com.eganin.jetpack.thebest.movieapp.presentation.view.screens.MovieDetailsActivity.Companion.SAVE_MOVIE_DATA_KEY


class FragmentMoviesDetails : Fragment() {

    private val idMovie: Int? by lazy { arguments?.getInt(SAVE_MOVIE_DATA_KEY) }
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val actorsAdapter = ActorAdapter()
    private val viewModel: MovieDetailsViewModel by viewModels { ViewModelFactory(context = requireContext()) }

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
        //setupUI(view = view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*
    private fun setupUI(view: View) {
        setupRecyclerView(view = view)
        setupListeners()
        with(binding) {
            with(movie) {
                downloadImage(
                    link = backdrop,
                    imageView = backgroundImage,
                    context = requireContext()
                )
                adultTv.text = "$minimumAge+"
                titleMovie.text = title
                tagLine.text = genres.joinToString(separator = ",") { it.name }
                countReviews.text = "$numberOfRatings REVIEWS"
                bindStars(rating = (ratings / 2).toInt())
                storylineDescription.text = overview
            }
        }
    }

     */

    private fun setupListeners() {
        binding.backBtb?.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.backBtbArrow?.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setupRecyclerView(view: View) {
        view.findViewById<RecyclerView>(R.id.actors_recycler_view).apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = actorsAdapter
            //actorsAdapter.bindActors(actors = )
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