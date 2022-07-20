package com.eganin.jetpack.thebest.movieapp.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.adapters.MovieAdapter
import com.eganin.jetpack.thebest.movieapp.data.models.Movie

class MovieViewHolder(itemView: View, listener: MovieAdapter.OnClickPoster?) :
    RecyclerView.ViewHolder(itemView) {

    init {
        itemView.apply {
            setOnClickListener {
                listener?.clickPoster(position = adapterPosition)
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
            timeLinePoster.text = "$timeLime MIN"
            countReviewsPoster.text = "$countReviews REVIEWS"
            adultMovie.text = ageRating
            tagLine.text = movie.tags.joinToString(separator = ",") { it }
            bindFavouriteMovie(isFavourite = isFavourite)
            bindStars(rating = starRating)
            downloadPoster(link = imageMovie)
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

    private fun downloadPoster(link: String) {
        with(Glide.with(context)) {
            clear(posterMovie)
            load(link).into(posterMovie)
        }

    }

}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context