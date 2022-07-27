package com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(

	@SerialName("genres")
	val genres: List<GenresItem>?=null,
)
@Serializable
data class GenresItem(

	@SerialName("name")
	val name: String?=null,

	@SerialName("id")
	val id: Int?=null,
)
