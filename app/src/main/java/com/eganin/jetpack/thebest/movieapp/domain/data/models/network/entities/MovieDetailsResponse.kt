package com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsResponse(

	@SerialName("original_language")
	val originalLanguage: String,

	@SerialName("imdb_id")
	val imdbId: String,

	@SerialName("video")
	val video: Boolean,

	@SerialName("title")
	val title: String,

	@SerialName("backdrop_path")
	val backdropPath: String,

	@SerialName("revenue")
	val revenue: Int,

	@SerialName("genres")
	val genres: List<GenresItemDetails>,

	@SerialName("popularity")
	val popularity: Double,

	@SerialName("production_countries")
	val productionCountries: List<ProductionCountriesItem>,

	@SerialName("id")
	val id: Int,

	@SerialName("vote_count")
	val voteCount: Int,

	@SerialName("budget")
	val budget: Int,

	@SerialName("overview")
	val overview: String,

	@SerialName("original_title")
	val originalTitle: String,

	@SerialName("runtime")
	val runtime: Int,

	@SerialName("poster_path")
	val posterPath: String,

	@SerialName("spoken_languages")
	val spokenLanguages: List<SpokenLanguagesItem>,

	@SerialName("production_companies")
	val productionCompanies: List<ProductionCompaniesItem>,

	@SerialName("release_date")
	val releaseDate: String,

	@SerialName("vote_average")
	val voteAverage: Double,


	@SerialName("tagline")
	val tagline: String,

	@SerialName("adult")
	val adult: Boolean,

	@SerialName("homepage")
	val homepage: String,

	@SerialName("status")
	val status: String
)
@Serializable
data class ProductionCompaniesItem(

	@SerialName("logo_path")
	val logoPath: String,

	@SerialName("name")
	val name: String,

	@SerialName("id")
	val id: Int,

	@SerialName("origin_country")
	val originCountry: String
)
@Serializable
data class SpokenLanguagesItem(

	@SerialName("name")
	val name: String,

	@SerialName("iso_639_1")
	val iso6391: String,

	@SerialName("english_name")
	val englishName: String
)
@Serializable
data class GenresItemDetails(

	@SerialName("name")
	val name: String,

	@SerialName("id")
	val id: Int
)
@Serializable
data class ProductionCountriesItem(

	@SerialName("iso_3166_1")
	val iso31661: String,

	@SerialName("name")
	val name: String
)
