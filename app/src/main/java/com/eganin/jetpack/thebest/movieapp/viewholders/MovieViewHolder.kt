package com.eganin.jetpack.thebest.movieapp.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.adapters.MovieAdapter
import com.eganin.jetpack.thebest.movieapp.data.models.Movie
import com.eganin.jetpack.thebest.movieapp.utils.downloadImage

class MovieViewHolder(itemView: View, listener: MovieAdapter.OnClickPoster?, movies : List<Movie>) :
    RecyclerView.ViewHolder(itemView) {

    init {
        itemView.apply {
            setOnClickListener {
                listener?.clickPoster(movie = movies[adapterPosition])
            }
        }
    }

    private val adultMovie: TextView = itemView.findViewById(R.id.adult_tv_movie_poster)
    private val favourite: ImageView = itemView.findViewById(R.id.like)
    private val posterMovie: ImageView = itemView.findViewById(R.id.movie_image)
    private val tagLine: TextView = itemView.findViewById(R.id.tag_line_movie_poster)
    private val countReviewsPoster: TextView =
        itemView.findViewById(R.id.count_reviews_movie_poster)
    private val listStarsRating: List<ImageView> = listOf(
        itemView.findViewById(R.id.one_star_movie_poster),
        itemView.findViewById(R.id.two_star_movie_poster),
        itemView.findViewById(R.id.three_star_movie_poster),
        itemView.findViewById(R.id.four_star_movie_poster),
        itemView.findViewById(R.id.five_star_movie_poster),
    )
    private val titlePoster: TextView = itemView.findViewById(R.id.name_movie_poster)
    private val timeLinePoster: TextView = itemView.findViewById(R.id.time_movie_poster)

    fun bind(movie: Movie) {
        with(movie) {
            titlePoster.text = title
            timeLinePoster.text = "$runtime MIN"
            countReviewsPoster.text = "$numberOfRatings REVIEWS"
            adultMovie.text = "$minimumAge+"
            tagLine.text = movie.genres.joinToString(separator = ",") { it.name }
            bindFavouriteMovie(isFavourite = true)
            bindStars(rating = (ratings/2).toInt())
            downloadImage(link = poster, context = context, imageView = posterMovie)
        }
    }

    private fun bindFavouriteMovie(isFavourite: Boolean) {
        if (isFavourite)
            favourite.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_like
                )
            )
        else favourite.setImageDrawable(
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