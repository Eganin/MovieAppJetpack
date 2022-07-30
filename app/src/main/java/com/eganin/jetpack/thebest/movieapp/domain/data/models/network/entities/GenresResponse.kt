package com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities

import androidx.room.ColumnInfo
import com.eganin.jetpack.thebest.movieapp.domain.data.database.Contract
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(

	@SerialName("genres")
	val genres: List<GenresItem>?=null,
)
@Serializable
data class GenresItem(

	@ColumnInfo(name = Contract.MovieDetails.COLUMN_NAME_NAME_GENRE)
	@SerialName("name")
	val name: String?=null,

	@ColumnInfo(name = Contract.MovieDetails.COLUMN_NAME_ID_GENRE)
	@SerialName("id")
	val id: Int?=null,
)
