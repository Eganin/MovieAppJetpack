package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.databinding.ViewHolderMovieBinding
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.MoviesApi.Companion.BASE_IMAGE_URL
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.GenresItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.Movie
import com.eganin.jetpack.thebest.movieapp.ui.presentation.utils.downloadImage
import kotlinx.coroutines.*

class MovieViewHolder(
    itemView: View,
    private val listener: MovieAdapter.OnClickPoster?,
    private val genres: List<GenresItem>?,
    private val usingDB: (Movie, Boolean) -> Unit,
    private val listenerExists: suspend (Int) -> Boolean,
) :
    RecyclerView.ViewHolder(itemView) {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val binding = ViewHolderMovieBinding.bind(itemView)

    private val listStarsRating: List<ImageView> = listOf(
        binding.oneStarMoviePoster,
        binding.twoStarMoviePoster,
        binding.threeStarMoviePoster,
        binding.fourStarMoviePoster,
        binding.fiveStarMoviePoster,
    )


    fun bind(movie: Movie, movies: List<Movie>) {
        with(movie) {
            with(binding) {
                nameMoviePoster.text = title
                timeMoviePoster
                countReviewsMoviePoster.text = "$voteCount REVIEWS"
                adult?.let { adultTvMoviePoster.text = if (it) "18+" else "12+" }
                genreIds?.let { tagLineMoviePoster.text = getTagLine(genreIds = it) }
                voteAverage?.let { bindStars(rating = (it / 2).toInt()) }
                bindLike(id = movie.id)
                downloadImage(
                    link = BASE_IMAGE_URL + posterPath,
                    context = context,
                    imageView = movieImage
                )
            }
        }
        itemView.setOnClickListener {
            listener?.clickPoster(idMovie = movies[adapterPosition].id)
        }


        binding.like.setOnClickListener {
            uiScope.launch {
                val answer = !listenerExists(movie.id)
                paintingLike(condition = answer)
                usingDB(movie, answer)
            }
        }
    }

    private fun getTagLine(genreIds: List<Int>) =
        genres?.filter { it.id in genreIds }?.joinToString { it.name ?: "" }

    private fun bindLike(id: Int) {
        scope.launch {
            coroutineScope {
                val exists = listenerExists(id)

                uiScope.launch {
                    paintingLike(condition = exists)
                }
            }
        }
    }

    private fun paintingLike(condition: Boolean) {
        if (condition) {
            binding.like.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_like
                )
            )
        } else {
            binding.like.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_like_unable
                )
            )
        }
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