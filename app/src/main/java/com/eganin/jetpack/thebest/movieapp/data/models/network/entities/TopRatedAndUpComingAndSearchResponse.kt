package com.eganin.jetpack.thebest.movieapp.data.models.network.entities

import com.google.gson.annotations.SerializedName

data class TopRatedAndUpComingAndSearchResponse(

	@field:SerializedName("page")
	val page: Int,

	@field:SerializedName("total_pages")
	val totalPages: Int,

	@field:SerializedName("results")
	val results: List<ResultsItemTopRatedAndUpComingAndSearch>,

	@field:SerializedName("total_results")
	val totalResults: Int
)

data class ResultsItemTopRatedAndUpComingAndSearch(

	@field:SerializedName("original_title")
	val originalTitle: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("genre_ids")
	val genreIds: List<Int>,

	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("backdrop_path")
	val backdropPath: String,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("adult")
	val adult: Boolean,

	@field:SerializedName("vote_count")
	val voteCount: Int
)
