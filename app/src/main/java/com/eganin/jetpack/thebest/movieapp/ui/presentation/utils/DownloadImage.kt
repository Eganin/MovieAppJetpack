package com.eganin.jetpack.thebest.movieapp.ui.presentation.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.eganin.jetpack.thebest.movieapp.R

fun downloadImage(link: String, imageView: ImageView, context: Context) {

    val imageOption = RequestOptions()
        .placeholder(R.drawable.ic_baseline_cloud_download_24)
        .fallback(R.drawable.ic_baseline_sms_failed_24)

    with(Glide.with(context)) {
        clear(imageView)
        load(link)
            .centerCrop()
            .apply(imageOption)
            .into(imageView)

    }
}