package com.eganin.jetpack.thebest.movieapp.domain.data.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.eganin.jetpack.thebest.movieapp.domain.data.database.Contract
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.GenresItem

@Entity(
    tableName = Contract.Movies.TABLE_NAME,
    indices = [Index(Contract.Movies.COLUMN_NAME_ID)]
)
data class MovieEntity(

    @ColumnInfo(name = Contract.Movies.COLUMN_NAME_ID)
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = Contract.Movies.COLUMN_NAME_ORIGINAL_TITLE)
    val originalTitle: String = "",

    @ColumnInfo(name = Contract.Movies.COLUMN_NAME_TITLE)
    val title: String = "",

    @ColumnInfo(name = Contract.Movies.COLUMN_NAME_GENRE_IDS)
    val genreIds: List<Int> = emptyList(),

    @ColumnInfo(name = Contract.Movies.COLUMN_NAME_POSTER_PATH)
    val posterPath: String = "",

    @ColumnInfo(name = Contract.Movies.COLUMN_NAME_BACKDROP_PATH)
    val backdropPath: String = "",

    @ColumnInfo(name = Contract.Movies.COLUMN_NAME_VOTE_AVERAGE)
    val voteAverage: Double = 0.0,

    @ColumnInfo(name = Contract.Movies.COLUMN_NAME_ADULT)
    val adult: Boolean = false,

    @ColumnInfo(name = Contract.Movies.COLUMN_NAME_VOTE_COUNT)
    val voteCount: Int = 0,

    @ColumnInfo(name = Contract.Movies.COLUMN_NAME_GENRES_LIST)
    val genres: List<GenresItem> = emptyList()
)