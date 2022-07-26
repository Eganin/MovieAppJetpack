package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.CastItem
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.viewholders.ActorViewHolder

class ActorAdapter : RecyclerView.Adapter<ActorViewHolder>() {


    private var actors: MutableList<CastItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ActorViewHolder(
        itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.view_holder_actor, parent, false
        )
    )

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) =
        holder.bind(actor = actors[position])

    override fun getItemCount()=actors.size

    fun bindActors(actors: List<CastItem>) {
        this.actors = actors.toMutableList()
    }

    fun addActors(newActors: List<CastItem>) {
        actors.addAll(newActors)
    }
}
