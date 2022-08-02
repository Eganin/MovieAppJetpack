package com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(

    @SerialName("page")
    val page: Int?=null,

    @SerialName("total_pages")
    val totalPages: Int?=null,

    @SerialName("results")
    val results: List<Movie>?=null,

    @SerialName("total_results")
    val totalResults: Int?=null
)
@Serializable
data class Movie(

    @SerialName("original_title")
    val originalTitle: String?=null,

    @SerialName("title")
    val title: String?=null,

    @SerialName("genre_ids")
    val genreIds: List<Int>?=null,

    @SerialName("poster_path")
    val posterPath: String?=null,

    @SerialName("backdrop_path")
    val backdropPath: String?=null,

    @SerialName("vote_average")
    val voteAverage: Double?=null,

    @SerialName("id")
    val id: Int,

    @SerialName("adult")
    val adult: Boolean?=null,

    @SerialName("vote_count")
    val voteCount: Int?=null
)
