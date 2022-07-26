package com.eganin.jetpack.thebest.movieapp.presentation.view.viewholders

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.data.models.network.MoviesApi.Companion.BASE_IMAGE_URL
import com.eganin.jetpack.thebest.movieapp.data.models.network.entities.Movie
import com.eganin.jetpack.thebest.movieapp.presentation.view.adapters.MovieAdapter
import com.eganin.jetpack.thebest.movieapp.databinding.ViewHolderMovieBinding
import com.eganin.jetpack.thebest.movieapp.presentation.utils.downloadImage
import com.eganin.jetpack.thebest.movieapp.presentation.view.fragments.list.MoviesListViewModel

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
                listener?.clickPoster(idMovie = movies[adapterPosition].id)
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
                adultTvMoviePoster.text = if (adult) "18+" else "12+"
                tagLineMoviePoster.text = getTagLine(genreIds = genreIds)
                bindFavouriteMovie(isFavourite = true)
                bindStars(rating = (voteAverage / 2).toInt())
                downloadImage(
                    link = BASE_IMAGE_URL + posterPath,
                    context = context,
                    imageView = movieImage
                )
            }
        }
    }

    fun getTagLine(genreIds: List<Int>) =
        moviesListViewModel.genresList?.filter { it.id in genreIds }?.joinToString { it.name }


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