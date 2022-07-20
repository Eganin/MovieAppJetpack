package com.eganin.jetpack.thebest.movieapp.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.data.models.Actor
import com.eganin.jetpack.thebest.movieapp.utils.downloadImage

class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageActor: ImageView = itemView.findViewById(R.id.image_cast)
    private val nameActor: TextView = itemView.findViewById(R.id.name_cast)

    fun bind(actor: Actor) {
        downloadImage(link = actor.image, context = context, imageView = imageActor)
        nameActor.text = actor.name
    }


}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context