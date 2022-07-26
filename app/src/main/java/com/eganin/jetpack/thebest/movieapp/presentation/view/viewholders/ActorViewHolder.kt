package com.eganin.jetpack.thebest.movieapp.presentation.view.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.eganin.jetpack.thebest.movieapp.data.models.network.entities.CastItem
import com.eganin.jetpack.thebest.movieapp.databinding.ViewHolderActorBinding
import com.eganin.jetpack.thebest.movieapp.presentation.utils.downloadImage

class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ViewHolderActorBinding.bind(itemView)

    fun bind(actor: CastItem) {
        with(binding) {
            downloadImage(link = actor.profilePath, context = context, imageView = imageCast)
            nameCast.text = actor.name
        }
    }


}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context