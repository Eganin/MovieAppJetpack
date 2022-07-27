package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.viewholders

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.MoviesApi.Companion.BASE_IMAGE_URL
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.Movie
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.adapters.MovieAdapter
import com.eganin.jetpack.thebest.movieapp.databinding.ViewHolderMovieBinding
import com.eganin.jetpack.thebest.movieapp.ui.presentation.utils.downloadImage
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.MoviesListViewModel

class MovieViewHolder(
    itemView: View,
    listener: MovieAdapter.OnClickPoster?,
    movies: List<Movie>,
    val moviesListViewModel: MoviesListViewModel
) :
    RecyclerView.ViewHolder(itemView) {

    init {
        itemView.apply {
            setOnClickListener {
                movies[adapterPosition].id?.let { listener?.clickPoster(idMovie = it) }
            }
        }

    }

    private val binding = ViewHolderMovieBinding.bind(itemView)

    private val listStarsRating: List<ImageView> = listOf(
        binding.oneStarMoviePoster,
        binding.twoStarMoviePoster,
        binding.threeStarMoviePoster,
        binding.fourStarMoviePoster,
        binding.fiveStarMoviePoster,
    )


    fun bind(movie: Movie) {
        with(movie) {
            with(binding) {
                nameMoviePoster.text = title
                timeMoviePoster
                countReviewsMoviePoster.text = "$voteCount REVIEWS"
                adult?.let { adultTvMoviePoster.text = if (it) "18+" else "12+" }
                genreIds?.let { tagLineMoviePoster.text = getTagLine(genreIds = it) }
                bindFavouriteMovie(isFavourite = true)
                voteAverage?.let { bindStars(rating = (it / 2).toInt()) }
                downloadImage(
                    link = BASE_IMAGE_URL + posterPath,
                    context = context,
                    imageView = movieImage
                )
            }
        }
    }

    private fun getTagLine(genreIds: List<Int>) =
        moviesListViewModel.genresList?.filter { it.id in genreIds }?.joinToString { it.name ?: "" }


    private fun bindFavouriteMovie(isFavourite: Boolean) {
        if (isFavourite)
            binding.like.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_like
                )
            )
        else binding.like.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.ic_like_unable
            )
        )
    }

    private fun bindStars(rating: Int) {
        for (i in 0 until rating) {
            listStarsRating[i].setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_star_icon
                )
            )
        }
    }

}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context