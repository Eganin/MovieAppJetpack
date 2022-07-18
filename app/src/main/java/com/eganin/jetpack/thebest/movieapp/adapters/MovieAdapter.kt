package com.eganin.jetpack.thebest.movieapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eganin.jetpack.thebest.movieapp.R

class MovieAdapter(private var movies: List<Int> = mutableListOf()) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var listener: OnClickPoster? = null

    interface OnClickPoster {
        fun click(position: Int)
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.apply {
                setOnClickListener {
                    listener?.click(position = adapterPosition)
                }
            }
        }

        fun bind(position: Int) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(
        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_movie, parent, false)
    )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) = holder.bind(position)

    override fun getItemCount() = movies.size
}