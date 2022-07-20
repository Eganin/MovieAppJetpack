package com.eganin.jetpack.thebest.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.data.models.Actor
import com.eganin.jetpack.thebest.movieapp.viewholders.ActorViewHolder

class ActorAdapter : RecyclerView.Adapter<ActorViewHolder>() {

    private var actors: MutableList<Actor> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ActorViewHolder(
        itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.view_holder_actor, parent, false
        )
    )

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) =
        holder.bind(actor = actors[position])

    override fun getItemCount()=actors.size

    fun bindActors(actors: List<Actor>) {
        this.actors = actors.toMutableList()
    }

    fun addActors(newActors: List<Actor>) {
        actors.addAll(newActors)
    }
}
