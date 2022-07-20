package com.eganin.jetpack.thebest.movieapp.data.models

data class Movie(
    val id : Int,
    val title : String,
    val ageRating: String,
    val isFavourite : Boolean,
    val tags : List<String>,
    val starRating : Int,
    val countReviews : Int ,
    val timeLime : Int,
    val imageMovie: String,
    val actors : List<Actor>
)