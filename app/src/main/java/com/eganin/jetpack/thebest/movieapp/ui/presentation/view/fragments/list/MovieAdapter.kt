package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.Movie

class MovieAdapter(private val moviesListViewModel: MoviesListViewModel) :
    RecyclerView.Adapter<MovieViewHolder>() {

    var listener: OnClickPoster? = null

    private var movies: MutableList<Movie> = mutableListOf()

    private val usingDBFavourite: (Movie, Boolean) -> Unit = { movie, condition ->
        moviesListViewModel.usingDBFavouriteMovie(movie = movie, condition = condition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(
        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_movie, parent, false),
        listener = listener,
        genres = moviesListViewModel.genresList,
        usingDB = usingDBFavourite,
    ) { moviesListViewModel.existsMovie(it) }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(movie = movies[position], movies = movies).also {
            if (position >= movies.size.minus(6) && movies.size >= 20) {
                if (!moviesListViewModel.isActiveDownload) moviesListViewModel.downloadMovies(
                    isAdapter = true
                )
            }
        }

    override fun getItemCount() = movies.size

    fun bindMovies(movies: List<Movie>) {
        this.movies = movies.toMutableList()
        notifyDataSetChanged()
    }

    interface OnClickPoster {
        fun clickPoster(idMovie: Int)
    }
}
