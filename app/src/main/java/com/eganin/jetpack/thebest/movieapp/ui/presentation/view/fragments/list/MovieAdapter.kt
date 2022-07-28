package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.Movie

class MovieAdapter(val moviesListViewModel: MoviesListViewModel) :
    RecyclerView.Adapter<MovieViewHolder>() {

    var listener: OnClickPoster? = null

    private var movies: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(
        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_movie, parent, false),
        listener = listener,
        moviesListViewModel = moviesListViewModel
    )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(movie = movies[position], movies = movies).also {
            if (position >= movies.size.minus(4) && movies.size >= 20) {
                moviesListViewModel.downloadMoviesList()
            }
        }

    override fun getItemCount() = movies.size

    fun bindMovies(movies: List<Movie>) {
        this.movies = movies.toMutableList()
        notifyDataSetChanged()
    }

    fun addMovies(newMovies: List<Movie>) {
        movies.addAll(newMovies)
    }


    interface OnClickPoster {
        fun clickPoster(idMovie: Int)
    }
}
