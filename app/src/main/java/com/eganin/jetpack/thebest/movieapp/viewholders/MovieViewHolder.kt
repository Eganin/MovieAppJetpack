package com.eganin.jetpack.thebest.movieapp.viewholders

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.adapters.MovieAdapter
import com.eganin.jetpack.thebest.movieapp.data.models.Movie
import com.eganin.jetpack.thebest.movieapp.databinding.ViewHolderMovieBinding
import com.eganin.jetpack.thebest.movieapp.utils.downloadImage

class MovieViewHolder(itemView: View, listener: MovieAdapter.OnClickPoster?, movies: List<Movie>) :
    RecyclerView.ViewHolder(itemView) {

    init {
        itemView.apply {
            setOnClickListener {
                listener?.clickPoster(movie = movies[adapterPosition])
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
                countReviewsMoviePoster.text = "$numberOfRatings REVIEWS"
                adultTvMoviePoster.text = "$minimumAge+"
                tagLineMoviePoster.text = movie.genres.joinToString(separator = ",") { it.name }

                bindFavouriteMovie(isFavourite = true)
                bindStars(rating = (ratings / 2).toInt())
                downloadImage(link = poster, context = context, imageView = movieImage)
            }
        }
    }

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