package com.eganin.jetpack.thebest.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.data.models.Movie
import com.eganin.jetpack.thebest.movieapp.viewholders.MovieViewHolder

class MovieAdapter :
    RecyclerView.Adapter<MovieViewHolder>() {

    var listener: OnClickPoster? = null

    private var movies: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(
        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_movie, parent, false),
        listener = listener
    )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(movie = movies[position])

    override fun getItemCount() = movies.size

    fun bindMovies(newMovies: List<Movie>) {
        movies = newMovies.toMutableList()
    }

    fun addMovies(newMovies: List<Movie>) {
        movies.addAll(newMovies)
    }

    interface OnClickPoster {
        fun clickPoster(position: Int)
    }
}
