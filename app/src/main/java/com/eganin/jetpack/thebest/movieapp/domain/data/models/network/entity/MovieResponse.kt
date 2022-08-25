package com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(

    @SerialName("page")
    val page: Int=0,

    @SerialName("total_pages")
    val totalPages: Int=0,

    @SerialName("results")
    val results: List<Movie> = emptyList(),

    @SerialName("total_results")
    val totalResults: Int=0
)
@Serializable
data class Movie(

    @SerialName("runtime")
    val runtime: Int = 0,

    @SerialName("original_title")
    val originalTitle: String ="",

    @SerialName("title")
    val title: String="",

    @SerialName("genre_ids")
    val genreIds: List<Int> = emptyList(),

    @SerialName("poster_path")
    val posterPath: String ="",

    @SerialName("backdrop_path")
    val backdropPath: String="",

    @SerialName("vote_average")
    val voteAverage: Double =0.0,

    @SerialName("id")
    val id: Int,

    @SerialName("adult")
    val adult: Boolean =false,

    @SerialName("vote_count")
    val voteCount: Int =0
)
