package com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.eganin.jetpack.thebest.movieapp.domain.data.database.Contract
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsMovies(

    @SerialName("cast")
    val cast: List<CastItem>? = null,

    @SerialName("id")
    val id: Int,
)

@Entity(
    tableName = Contract.Credits.TABLE_NAME,
    indices = [Index(Contract.Credits.COLUMN_NAME_ID)]
)
@Serializable
data class CastItem(

    @ColumnInfo(name = Contract.Credits.COLUMN_CHARACTER)
    @SerialName("character")
    val character: String? = null,

    @ColumnInfo(name = Contract.Credits.COLUMN_ORIGINAL_NAME)
    @SerialName("original_name")
    val originalName: String? = null,

    @ColumnInfo(name = Contract.Credits.COLUMN_NAME)
    @SerialName("name")
    val name: String? = null,

    @ColumnInfo(name = Contract.Credits.COLUMN_PROFILE_PATH)
    @SerialName("profile_path")
    val profilePath: String? = null,

    @ColumnInfo(name = Contract.Credits.COLUMN_NAME_ID)
    @PrimaryKey(autoGenerate = true)
    @SerialName("id")
    val id: Int,

    @ColumnInfo(name = Contract.Credits.COLUMN_ID_BY_MOVIE)
    var movieId : Int?=null,
    )