package com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(

    @SerialName("page")
    val page: Int,

    @SerialName("total_pages")
    val totalPages: Int,

    @SerialName("results")
    val results: List<Movie>,

    @SerialName("total_results")
    val totalResults: Int
)
@Serializable
data class Movie(

    @SerialName("original_title")
    val originalTitle: String,

    @SerialName("title")
    val title: String,

    @SerialName("genre_ids")
    val genreIds: List<Int>,

    @SerialName("poster_path")
    val posterPath: String,

    @SerialName("backdrop_path")
    val backdropPath: String?=null,

    @SerialName("vote_average")
    val voteAverage: Double,

    @SerialName("id")
    val id: Int,

    @SerialName("adult")
    val adult: Boolean,

    @SerialName("vote_count")
    val voteCount: Int
)
