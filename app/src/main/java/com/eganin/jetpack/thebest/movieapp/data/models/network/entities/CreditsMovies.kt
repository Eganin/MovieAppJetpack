package com.eganin.jetpack.thebest.movieapp.data.models.network.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsMovies(

	@SerialName("cast")
	val cast: List<CastItem>,

	@SerialName("id")
	val id: Int,

	@SerialName("crew")
	val crew: List<CrewItem>
)
@Serializable
data class CastItem(

	@SerialName("cast_id")
	val castId: Int,

	@SerialName("character")
	val character: String,

	@SerialName("gender")
	val gender: Int,

	@SerialName("credit_id")
	val creditId: String,

	@SerialName("known_for_department")
	val knownForDepartment: String,

	@SerialName("original_name")
	val originalName: String,

	@SerialName("popularity")
	val popularity: Double,

	@SerialName("name")
	val name: String,

	@SerialName("profile_path")
	val profilePath: String,

	@SerialName("id")
	val id: Int,

	@SerialName("adult")
	val adult: Boolean,

	@SerialName("order")
	val order: Int
)
@Serializable
data class CrewItem(

	@SerialName("gender")
	val gender: Int,

	@SerialName("credit_id")
	val creditId: String,

	@SerialName("known_for_department")
	val knownForDepartment: String,

	@SerialName("original_name")
	val originalName: String,

	@SerialName("popularity")
	val popularity: Double,

	@SerialName("name")
	val name: String,

	@SerialName("profile_path")
	val profilePath: String?=null,

	@SerialName("id")
	val id: Int,

	@SerialName("adult")
	val adult: Boolean,

	@SerialName("department")
	val department: String,

	@SerialName("job")
	val job: String
)
