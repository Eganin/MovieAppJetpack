package com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsResponse(

    @SerialName("title")
    val title: String? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("genres")
    val genres: List<GenresItemDetails>? = null,

    @SerialName("popularity")
    val popularity: Double? = null,

    @SerialName("id")
    val id: Int? = null,

    @SerialName("vote_count")
    val voteCount: Int? = null,

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("original_title")
    val originalTitle: String? = null,

    @SerialName("runtime")
    val runtime: Int? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("vote_average")
    val voteAverage: Double? = null,

    @SerialName("adult")
    val adult: Boolean? = null,

    )

@Serializable
data class GenresItemDetails(

    @SerialName("name")
    val name: String? = null,

    @SerialName("id")
    val id: Int? = null
)

