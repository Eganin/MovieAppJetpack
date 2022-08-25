package com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsResponse(

    @SerialName("title")
    val title: String = "",

    @SerialName("backdrop_path")
    val backdropPath: String = "",

    @SerialName("genres")
    val genres: List<GenresItem> = emptyList(),

    @SerialName("popularity")
    val popularity: Double = 0.0,

    @SerialName("id")
    val id: Int,

    @SerialName("vote_count")
    val voteCount: Int = 0,

    @SerialName("overview")
    val overview: String = "",

    @SerialName("original_title")
    val originalTitle: String = "",

    @SerialName("runtime")
    val runtime: Int = 0,

    @SerialName("poster_path")
    val posterPath: String ="",

    @SerialName("vote_average")
    val voteAverage: Double = 0.0,

    @SerialName("adult")
    val adult: Boolean =false,
    )


